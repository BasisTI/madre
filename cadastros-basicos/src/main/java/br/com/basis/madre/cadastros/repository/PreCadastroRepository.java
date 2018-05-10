package br.com.basis.madre.cadastros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.PreCadastro;


/**
 * Spring Data JPA repository for the PreCadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreCadastroRepository extends JpaRepository<PreCadastro, Long> {
}
