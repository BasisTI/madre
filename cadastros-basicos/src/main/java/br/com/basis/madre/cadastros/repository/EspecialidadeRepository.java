package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Especialidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Optional<Especialidade> findOneByNomeIgnoreCase(String nome);
    Optional<Especialidade> findOneByIdAndNomeIgnoreCase(Long id, String nome);
}
