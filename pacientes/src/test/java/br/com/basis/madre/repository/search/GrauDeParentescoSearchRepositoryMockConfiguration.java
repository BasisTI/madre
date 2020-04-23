package br.com.basis.madre.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GrauDeParentescoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GrauDeParentescoSearchRepositoryMockConfiguration {

    @MockBean
    private GrauDeParentescoSearchRepository mockGrauDeParentescoSearchRepository;

}
