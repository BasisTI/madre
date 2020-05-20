package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ViasAdministracao} entity.
 */
public interface ViasAdministracaoSearchRepository extends ElasticsearchRepository<ViasAdministracao, Long> {
}
