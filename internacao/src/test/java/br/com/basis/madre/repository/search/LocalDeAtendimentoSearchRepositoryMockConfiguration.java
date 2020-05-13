package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LocalDeAtendimentoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LocalDeAtendimentoSearchRepositoryMockConfiguration {

    @MockBean
    private LocalDeAtendimentoSearchRepository mockLocalDeAtendimentoSearchRepository;

}
