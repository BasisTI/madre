package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoProcedimento} entity.
 */
public interface PrescricaoProcedimentoSearchRepository extends ElasticsearchRepository<PrescricaoProcedimento, Long> {
}
