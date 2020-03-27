package br.com.basis.madre.prescricao.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.domain.Paciente;


public interface PacienteRepository extends ElasticsearchRepository<Paciente, Long>  {

}
