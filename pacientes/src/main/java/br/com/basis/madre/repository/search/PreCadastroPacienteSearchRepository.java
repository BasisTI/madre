package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.PreCadastroPaciente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PreCadastroPaciente} entity.
 */
public interface PreCadastroPacienteSearchRepository extends ElasticsearchRepository<PreCadastroPaciente, Long> {
}
