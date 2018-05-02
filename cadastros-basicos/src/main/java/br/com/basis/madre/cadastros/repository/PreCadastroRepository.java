package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.PreCadastro;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PreCadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreCadastroRepository extends JpaRepository<PreCadastro, Long> {
	Optional<PreCadastro> findOneBynomeDoPaciente(String nome_do_paciente);
	
	Optional<PreCadastro> findOneBynomeDaMae(String nome_da_mae);
	
	Optional<PreCadastro> findOneBydataDeNascimento(LocalDate data_de_nascimento);
}
