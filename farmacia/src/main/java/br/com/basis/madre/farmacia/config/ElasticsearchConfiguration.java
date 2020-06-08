package br.com.basis.madre.farmacia.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.mapper.DefaultJestResultsMapper;
import io.searchbox.client.JestClient;
import org.hibernate.MappingException;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfiguration {

    private ObjectMapper mapper;

    public ElasticsearchConfiguration(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public EntityMapper getEntityMapper() {
        return new CustomEntityMapper(mapper);
    }

    @Bean
    @Primary
    public ElasticsearchOperations elasticsearchTemplate(final JestClient jestClient,
                                                         final ElasticsearchConverter elasticsearchConverter,
                                                         final SimpleElasticsearchMappingContext simpleElasticsearchMappingContext,
                                                         EntityMapper mapper) {
        return new JestElasticsearchTemplate(
            jestClient,
            elasticsearchConverter,
            new DefaultJestResultsMapper(simpleElasticsearchMappingContext, mapper));
    }




}
