package br.com.basis.suprimentos.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LancamentoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LancamentoSearchRepositoryMockConfiguration {

    @MockBean
    private LancamentoSearchRepository mockLancamentoSearchRepository;

}
