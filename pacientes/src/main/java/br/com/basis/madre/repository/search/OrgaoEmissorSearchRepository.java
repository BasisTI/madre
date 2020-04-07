package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.OrgaoEmissor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrgaoEmissor} entity.
 */
public interface OrgaoEmissorSearchRepository extends ElasticsearchRepository<OrgaoEmissor, Long> {
}
