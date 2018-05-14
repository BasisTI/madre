package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UnidadeHospitalar entity.
 */
public interface UnidadeHospitalarSearchRepository extends ElasticsearchRepository<UnidadeHospitalar, Long> {
}
