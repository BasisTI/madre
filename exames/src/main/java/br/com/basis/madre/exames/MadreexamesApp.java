package br.com.basis.madre.exames;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import br.gov.nuvem.comum.microsservico.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class})
@RequiredArgsConstructor
@Slf4j
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

