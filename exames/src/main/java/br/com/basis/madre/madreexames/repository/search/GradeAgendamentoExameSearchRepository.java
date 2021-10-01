package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GradeAgendamentoExame} entity.
 */
public interface GradeAgendamentoExameSearchRepository extends ElasticsearchRepository<GradeAgendamentoExame, Long> {
}
