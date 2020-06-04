package br.com.basis.madre.repository;
import br.com.basis.madre.domain.PreCadastroPaciente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PreCadastroPaciente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreCadastroPacienteRepository extends JpaRepository<PreCadastroPaciente, Long> {

}
