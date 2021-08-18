package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.GradeDeAgendamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GradeDeAgendamento} entity.
 */
public interface GradeDeAgendamentoSearchRepository extends ElasticsearchRepository<GradeDeAgendamento, Long> {
}
