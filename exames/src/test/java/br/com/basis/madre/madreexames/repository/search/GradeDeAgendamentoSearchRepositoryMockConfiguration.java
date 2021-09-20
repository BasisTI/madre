package br.com.basis.madre.madreexames.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GradeDeAgendamentoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GradeDeAgendamentoSearchRepositoryMockConfiguration {

    @MockBean
    private GradeDeAgendamentoSearchRepository mockGradeDeAgendamentoSearchRepository;

}
