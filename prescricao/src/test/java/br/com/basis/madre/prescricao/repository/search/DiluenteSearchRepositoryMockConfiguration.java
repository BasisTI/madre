package br.com.basis.madre.prescricao.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DiluenteSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DiluenteSearchRepositoryMockConfiguration {

    @MockBean
    private DiluenteSearchRepository mockDiluenteSearchRepository;

}
