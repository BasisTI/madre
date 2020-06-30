package br.com.basis.consulta.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FormaDeAgendamentoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FormaDeAgendamentoSearchRepositoryMockConfiguration {

    @MockBean
    private FormaDeAgendamentoSearchRepository mockFormaDeAgendamentoSearchRepository;

}
