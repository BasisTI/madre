package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.OrgaoEmissor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link OrgaoEmissor} entity.
 */
public interface OrgaoEmissorSearchRepository extends ElasticsearchRepository<OrgaoEmissor, Long> {
}
