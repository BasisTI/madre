package br.com.basis.suprimentos.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CaracteristicaArmazenamentoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CaracteristicaArmazenamentoSearchRepositoryMockConfiguration {

    @MockBean
    private CaracteristicaArmazenamentoSearchRepository mockCaracteristicaArmazenamentoSearchRepository;

}
