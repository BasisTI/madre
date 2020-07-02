package br.com.basis.suprimentos.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ItemTransferenciaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ItemTransferenciaSearchRepositoryMockConfiguration {

    @MockBean
    private ItemTransferenciaSearchRepository mockItemTransferenciaSearchRepository;

}
