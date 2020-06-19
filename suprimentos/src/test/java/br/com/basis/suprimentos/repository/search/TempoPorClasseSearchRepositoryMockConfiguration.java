package br.com.basis.suprimentos.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TempoPorClasseSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TempoPorClasseSearchRepositoryMockConfiguration {

    @MockBean
    private TempoPorClasseSearchRepository mockTempoPorClasseSearchRepository;

}
