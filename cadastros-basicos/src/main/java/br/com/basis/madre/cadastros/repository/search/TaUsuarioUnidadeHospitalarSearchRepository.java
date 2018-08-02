package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaUsuarioUnidadeHospitalar entity.
 */
public interface TaUsuarioUnidadeHospitalarSearchRepository extends ElasticsearchRepository<TaUsuarioUnidadeHospitalar, Long> {
}
