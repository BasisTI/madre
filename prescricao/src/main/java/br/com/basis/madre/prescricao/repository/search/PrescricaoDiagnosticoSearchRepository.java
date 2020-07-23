package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoDiagnostico} entity.
 */
public interface PrescricaoDiagnosticoSearchRepository extends ElasticsearchRepository<PrescricaoDiagnostico, Long> {
}
