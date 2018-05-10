package br.com.basis.madre.cadastros.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;

/**
 * Spring Data Elasticsearch repository for the UnidadeHospitalar entity.
 */
public interface UnidadeHospitalarSearchRepository extends ElasticsearchRepository<UnidadeHospitalar, Long> {
}
