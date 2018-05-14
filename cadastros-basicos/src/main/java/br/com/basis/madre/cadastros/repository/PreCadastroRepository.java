package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.service.dto.PreCadastroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data JPA repository for the PreCadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreCadastroRepository extends JpaRepository<PreCadastro, Long> {
    Optional<PreCadastroDTO> findOneBynomeDoPacienteIgnoreCaseAndNomeDaMaeIgnoreCaseAndDataDeNascimento(String nomeDoPaciente, String nomeDaMae, LocalDate dataDeNascimento);
}
