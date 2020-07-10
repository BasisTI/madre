package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Paciente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PacienteSearchRepository extends ElasticsearchRepository<Paciente, Long> {
    Optional<Paciente> findByProntuario(Long prontuario);
}
