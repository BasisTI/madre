package br.com.basis.madre.seguranca.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InstituicaoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InstituicaoSearchRepositoryMockConfiguration {

    @MockBean
    private InstituicaoSearchRepository mockInstituicaoSearchRepository;

}
