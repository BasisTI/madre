package br.com.basis.madre.cadastros.config;

import javax.annotation.PreDestroy;

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

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
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
        CacheManager cacheManager = new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
        return cacheManager;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(JHipsterProperties jHipsterProperties) {
        log.debug("Configuring Hazelcast");
        HazelcastInstance hazelCastInstance = Hazelcast.getHazelcastInstanceByName("cadastrosbasicos");
        if (hazelCastInstance != null) {
            log.debug("Hazelcast already initialized");
            return hazelCastInstance;
        }
        Config config = new Config();
        config.setInstanceName("cadastrosbasicos");
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        if (this.registration == null) {
            log.warn("No discovery service is set up, Hazelcast cannot create a cluster.");
        } else {
            String serviceId = registration.getServiceId();
            log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);
            if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
                log.debug("Application is running with the \"dev\" profile, Hazelcast " +"cluster will only work with localhost instances");
                System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
                config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
                    log.debug("Adding Hazelcast (dev) cluster member " + clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            } else { // Production configuration, one host per instance all using port 5701
                config.getNetworkConfig().setPort(5701);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = instance.getHost() + ":5701";
                    log.debug("Adding Hazelcast (prod) cluster member " + clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            }
        }
        config.getMapConfigs().put("default", initializeDefaultMapConfig());
        config.setManagementCenterConfig(initializeDefaultManagementCenterConfig(jHipsterProperties));
        config.getMapConfigs().put("br.com.basis.madre.cadastros.domain.*", initializeDomainMapConfig(jHipsterProperties));
        return Hazelcast.newHazelcastInstance(config);
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

        mapConfig.setBackupCount(0);


        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);


        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(JHipsterProperties jHipsterProperties) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(jHipsterProperties.getCache().getHazelcast().getTimeToLiveSeconds());
        return mapConfig;
    }
}
