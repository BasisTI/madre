package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InternacaoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InternacaoSearchRepositoryMockConfiguration {

    @MockBean
    private InternacaoSearchRepository mockInternacaoSearchRepository;

}
