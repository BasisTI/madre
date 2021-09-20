package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.HorarioLivre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link HorarioLivre} entity.
 */
public interface HorarioLivreSearchRepository extends ElasticsearchRepository<HorarioLivre, Long> {
}
