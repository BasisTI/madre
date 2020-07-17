package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Almoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Almoxarifado} entity.
 */
public interface AlmoxarifadoSearchRepository extends ElasticsearchRepository<Almoxarifado, Long> {
}
