package br.com.basis.madre.cadastros.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.PreCadastro;


/**
Spring Data JPA repository for the PreCadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreCadastroRepository extends JpaRepository<PreCadastro, Long> {
    Optional<PreCadastro> findOneByNomeDoPacienteIgnoreCaseAndNomeDaMaeIgnoreCaseAndDataDeNascimento (String nomeDoPaciente, String nomeDaMae, LocalDate dataDeNascimento);
    Optional<PreCadastro> findOneByNumCartaoSus (String numCartaoSus);
    Optional<PreCadastro> findOneByIdAndNomeDoPacienteIgnoreCaseAndNomeDaMaeIgnoreCaseAndDataDeNascimento (Long id, String nomeDoPaciente, String nomeDaMae, LocalDate dataDeNascimento);
    Optional<PreCadastro> findOneByIdAndNumCartaoSus (Long id, String numCartaoSus);
}
