package br.com.basis.madre.seguranca.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link UsuarioSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UsuarioSearchRepositoryMockConfiguration {

    @MockBean
    private UsuarioSearchRepository mockUsuarioSearchRepository;

}
