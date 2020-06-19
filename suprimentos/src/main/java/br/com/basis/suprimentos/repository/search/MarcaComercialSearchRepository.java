package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.MarcaComercial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MarcaComercial} entity.
 */
public interface MarcaComercialSearchRepository extends ElasticsearchRepository<MarcaComercial, Long> {
}
