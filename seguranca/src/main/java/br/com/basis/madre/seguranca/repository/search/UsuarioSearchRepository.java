package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Usuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Usuario} entity.
 */
public interface UsuarioSearchRepository extends ElasticsearchRepository<Usuario, Long> {
}
