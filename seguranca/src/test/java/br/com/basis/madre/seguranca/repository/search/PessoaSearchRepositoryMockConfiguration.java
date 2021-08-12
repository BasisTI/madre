package br.com.basis.madre.seguranca.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PessoaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PessoaSearchRepositoryMockConfiguration {

    @MockBean
    private PessoaSearchRepository mockPessoaSearchRepository;

}
