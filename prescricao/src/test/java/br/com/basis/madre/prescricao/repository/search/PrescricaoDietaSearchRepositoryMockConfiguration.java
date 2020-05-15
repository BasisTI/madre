package br.com.basis.madre.prescricao.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PrescricaoDietaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PrescricaoDietaSearchRepositoryMockConfiguration {

    @MockBean
    private PrescricaoDietaSearchRepository mockPrescricaoDietaSearchRepository;

}
