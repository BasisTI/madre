package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Perfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Perfil entity.
 */
public interface PerfilSearchRepository extends ElasticsearchRepository<Perfil, Long> {
}
