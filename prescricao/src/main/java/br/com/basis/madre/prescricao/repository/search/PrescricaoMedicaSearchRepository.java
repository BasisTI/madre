package br.com.basis.madre.prescricao.repository.search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.domain.PrescricaoMedica;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoMedica} entity.
 */
public interface PrescricaoMedicaSearchRepository extends ElasticsearchRepository<PrescricaoMedica, Long> {
	
}
