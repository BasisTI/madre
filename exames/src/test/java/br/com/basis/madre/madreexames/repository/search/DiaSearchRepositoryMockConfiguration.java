package br.com.basis.madre.madreexames.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DiaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DiaSearchRepositoryMockConfiguration {

    @MockBean
    private DiaSearchRepository mockDiaSearchRepository;

}
