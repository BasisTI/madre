package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Usuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Usuario entity.
 */
public interface UsuarioSearchRepository extends ElasticsearchRepository<Usuario, Long> {
}
