package br.com.basis.madre.farmacia.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EstornoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EstornoSearchRepositoryMockConfiguration {

    @MockBean
    private EstornoSearchRepository mockEstornoSearchRepository;

}
