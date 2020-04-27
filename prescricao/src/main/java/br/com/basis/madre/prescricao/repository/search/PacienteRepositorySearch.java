package br.com.basis.madre.prescricao.repository.search;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.Paciente;

@Repository
public interface PacienteRepositorySearch extends ElasticsearchRepository<Paciente, Long>  {
	Optional<Paciente> findById(Long id); 
}
