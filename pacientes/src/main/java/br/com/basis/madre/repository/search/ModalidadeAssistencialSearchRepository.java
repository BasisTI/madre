package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.ModalidadeAssistencial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ModalidadeAssistencial} entity.
 */
public interface ModalidadeAssistencialSearchRepository extends ElasticsearchRepository<ModalidadeAssistencial, Long> {
}
