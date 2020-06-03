package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Paciente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PacienteSearchRepository extends ElasticsearchRepository<Paciente, Long> {
}
