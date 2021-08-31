package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Graduacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Graduacao} entity.
 */
public interface GraduacaoSearchRepository extends ElasticsearchRepository<Graduacao, Long> {
}
