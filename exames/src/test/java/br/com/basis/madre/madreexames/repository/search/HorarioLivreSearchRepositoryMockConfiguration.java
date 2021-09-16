package br.com.basis.madre.madreexames.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link HorarioLivreSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HorarioLivreSearchRepositoryMockConfiguration {

    @MockBean
    private HorarioLivreSearchRepository mockHorarioLivreSearchRepository;

}
