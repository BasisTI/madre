package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GradeAgendamentoExame} entity.
 */
public interface GradeAgendamentoExameSearchRepository extends ElasticsearchRepository<GradeAgendamentoExameDTO, Long> {
}
