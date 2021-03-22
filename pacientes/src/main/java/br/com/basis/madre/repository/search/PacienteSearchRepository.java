package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Paciente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Spring Data Elasticsearch repository for the {@link Paciente} entity.
 */
public interface PacienteSearchRepository extends ElasticsearchRepository<Paciente, Long> {

    List<Paciente> findByProntuario(Long prontuario);
}
