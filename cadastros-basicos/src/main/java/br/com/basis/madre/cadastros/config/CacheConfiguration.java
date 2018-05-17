package br.com.basis.madre.cadastros.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = {MetricsConfiguration.class})
@AutoConfigureBefore(value = {WebConfigurer.class, DatabaseConfiguration.class})
public class CacheConfiguration {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private final Environment env;

    private final ServerProperties serverProperties;

    private final DiscoveryClient discoveryClient;

    private Registration registration;

    public CacheConfiguration(Environment env, ServerProperties serverProperties, DiscoveryClient discoveryClient) {
        this.env = env;
        this.serverProperties = serverProperties;
        this.discoveryClient = discoveryClient;
    }

    @Autowired(required = false)
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @PreDestroy
    public void destroy() {
        log.info("Closing Cache Manager");
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        log.debug("Starting HazelcastCacheManager");
        return new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(JHipsterProperties jHipsterProperties) {
        log.debug("Configuring Hazelcast");
        HazelcastInstance hazelCastInstance = Hazelcast.getHazelcastInstanceByName("cadastrosbasicos");
        if (hazelCastInstance != null) {
            log.debug("Hazelcast already initialized");
            return hazelCastInstance;
        }

        Config config = configura();
        config.getMapConfigs().put("default", initializeDefaultMapConfig());

        // Full reference is available at: http://docs.hazelcast.org/docs/management-center/3.9/manual/html/Deploying_and_Starting.html
        config.setManagementCenterConfig(initializeDefaultManagementCenterConfig(jHipsterProperties));
        config.getMapConfigs().put("br.com.basis.madre.cadastros.domain.*", initializeDomainMapConfig(jHipsterProperties));
        return Hazelcast.newHazelcastInstance(config);
    }

    private Config configura(){
        Config config = new Config();
        config.setInstanceName("cadastrosbasicos");
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        if (this.registration == null) {
            log.warn("No discovery service is set up, Hazelcast cannot create a cluster.");
        }
         else {
            String serviceId = registration.getServiceId();
            log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);
            // In development, everything goes through 127.0.0.1, with a different port
            if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
                configuration(config,serviceId);
            } else {
                configurationServer(config, serviceId);
            }
        }
        return config;
    }

    private void configuration(Config config, String serviceId){
        log.debug("Application is running with the \"dev\" profile, Hazelcast " + "cluster will only work with localhost instances");
        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
        config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
            String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
            String logServer = "Adding Hazelcast (dev) cluster member " + clusterMember;
            log.debug(logServer);
            config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
        }
    }
    private void configurationServer(Config config, String serviceId){
        config.getNetworkConfig().setPort(5701);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
            String clusterMember = instance.getHost() + ":5701";
            String logServer = "Adding Hazelcast (dev) cluster member " + clusterMember;
            log.debug(logServer);
            config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
        }
    }
    private ManagementCenterConfig initializeDefaultManagementCenterConfig(JHipsterProperties jHipsterProperties) {
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setEnabled(jHipsterProperties.getCache().getHazelcast().getManagementCenter().isEnabled());
        managementCenterConfig.setUrl(jHipsterProperties.getCache().getHazelcast().getManagementCenter().getUrl());
        managementCenterConfig.setUpdateInterval(jHipsterProperties.getCache().getHazelcast().getManagementCenter().getUpdateInterval());
        return managementCenterConfig;
    }

    private MapConfig initializeDefaultMapConfig() {
        MapConfig mapConfig = new MapConfig();

    /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
     */
        mapConfig.setBackupCount(0);

    /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
     */
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

    /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
     */
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(JHipsterProperties jHipsterProperties) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(jHipsterProperties.getCache().getHazelcast().getTimeToLiveSeconds());
        return mapConfig;
    }
}
