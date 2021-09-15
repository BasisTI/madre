package br.com.basis.madre.seguranca.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ConselhosProfissionaisSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ConselhosProfissionaisSearchRepositoryMockConfiguration {

    @MockBean
    private ConselhosProfissionaisSearchRepository mockConselhosProfissionaisSearchRepository;

}
