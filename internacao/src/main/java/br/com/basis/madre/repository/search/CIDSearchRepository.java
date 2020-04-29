package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.CID;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CID} entity.
 */
public interface CIDSearchRepository extends ElasticsearchRepository<CID, Long> {
}
