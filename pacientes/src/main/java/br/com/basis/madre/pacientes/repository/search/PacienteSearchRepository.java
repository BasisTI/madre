package br.com.basis.madre.pacientes.repository.search;

import br.com.basis.madre.pacientes.domain.Paciente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Paciente entity.
 */
public interface PacienteSearchRepository extends ElasticsearchRepository<Paciente, Long> {
}
