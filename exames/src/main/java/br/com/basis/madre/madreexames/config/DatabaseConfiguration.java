package br.com.basis.madre.madreexames.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("br.com.basis.madre.madreexames.repository")
@EnableTransactionManagement
@EnableElasticsearchRepositories("br.com.basis.madre.madreexames.repository.search")
public class DatabaseConfiguration {

}
