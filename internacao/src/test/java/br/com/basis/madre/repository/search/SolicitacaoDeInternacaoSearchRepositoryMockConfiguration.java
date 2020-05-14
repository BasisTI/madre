package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SolicitacaoDeInternacaoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SolicitacaoDeInternacaoSearchRepositoryMockConfiguration {

    @MockBean
    private SolicitacaoDeInternacaoSearchRepository mockSolicitacaoDeInternacaoSearchRepository;

}
