package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Certidao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Certidao} entity.
 */
public interface CertidaoSearchRepository extends ElasticsearchRepository<Certidao, Long> {
}
