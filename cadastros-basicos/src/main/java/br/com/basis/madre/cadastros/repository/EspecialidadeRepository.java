package br.com.basis.madre.cadastros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.Especialidade;;


/**
 * Spring Data JPA repository for the Especialidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Optional<Especialidade> findOneByNomeIgnoreCase(String nome);
    Optional<Especialidade> findOneByIdAndNomeIgnoreCase(Long id, String nome);
}
