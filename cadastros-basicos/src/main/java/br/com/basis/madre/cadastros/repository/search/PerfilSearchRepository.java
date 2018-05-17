package br.com.basis.madre.cadastros.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.cadastros.domain.Perfil;

/**
 * Spring Data Elasticsearch repository for the Perfil entity.
 */
public interface PerfilSearchRepository extends ElasticsearchRepository<Perfil, Long> {
}
