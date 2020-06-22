package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransferenciaAlmoxarifado} entity.
 */
public interface TransferenciaAlmoxarifadoSearchRepository extends ElasticsearchRepository<TransferenciaAlmoxarifado, Long> {
}
