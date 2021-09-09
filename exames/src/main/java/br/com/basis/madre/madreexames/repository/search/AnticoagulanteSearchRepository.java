package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Anticoagulante;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Anticoagulante} entity.
 */
public interface AnticoagulanteSearchRepository extends ElasticsearchRepository<Anticoagulante, Long> {
}
