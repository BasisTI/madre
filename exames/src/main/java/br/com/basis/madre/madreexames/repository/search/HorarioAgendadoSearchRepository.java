package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link HorarioAgendado} entity.
 */
public interface HorarioAgendadoSearchRepository extends ElasticsearchRepository<HorarioAgendadoDTO, Long> {
}
