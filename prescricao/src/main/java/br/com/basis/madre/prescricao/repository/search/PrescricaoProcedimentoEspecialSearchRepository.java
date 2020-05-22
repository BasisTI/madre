package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoProcedimentoEspecial} entity.
 */
public interface PrescricaoProcedimentoEspecialSearchRepository extends ElasticsearchRepository<PrescricaoProcedimentoEspecial, Long> {
}
