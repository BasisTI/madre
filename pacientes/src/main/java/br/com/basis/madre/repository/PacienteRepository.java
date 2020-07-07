package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.service.projection.PacienteResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Paciente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<PacienteResumo> findAllProjectedPacienteResumoByNomeContainingIgnoreCase(String nome,Pageable pageable);

    Optional<Paciente> findByProntuario(Long prontuario);

    @Query(value = "select nextval('seq_num_prontuario') ", nativeQuery =
        true)
    Long gerarProntuario();
}
