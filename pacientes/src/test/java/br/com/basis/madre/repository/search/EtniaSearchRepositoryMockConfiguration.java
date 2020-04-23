package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EtniaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EtniaSearchRepositoryMockConfiguration {

    @MockBean
    private EtniaSearchRepository mockEtniaSearchRepository;

}
