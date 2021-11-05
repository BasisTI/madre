package br.com.basis.madre.madreexames;


import br.gov.nuvem.comum.microsservico.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({LiquibaseProperties.class})
@RequiredArgsConstructor
@EnableAsync
public class MadreexamesApp implements InitializingBean {

    private final Environment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        AppUtil.checkProfiles(env, log);
    }

    public static void main(String[] args) {
        AppUtil.startup(args, MadreexamesApp.class, log);
    }
}

