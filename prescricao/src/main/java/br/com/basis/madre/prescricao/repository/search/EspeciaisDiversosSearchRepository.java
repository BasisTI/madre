package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.EspeciaisDiversos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EspeciaisDiversos} entity.
 */
public interface EspeciaisDiversosSearchRepository extends ElasticsearchRepository<EspeciaisDiversos, Long> {
}
