package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.CodigoCatmat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CodigoCatmat} entity.
 */
public interface CodigoCatmatSearchRepository extends ElasticsearchRepository<CodigoCatmat, Long> {
}
