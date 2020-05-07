package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OrigemDaReservaDeLeitoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OrigemDaReservaDeLeitoSearchRepositoryMockConfiguration {

    @MockBean
    private OrigemDaReservaDeLeitoSearchRepository mockOrigemDaReservaDeLeitoSearchRepository;

}
