package br.com.basis.madre.repository;

import br.com.basis.madre.domain.MotivoDoCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MotivoDoCadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivoDoCadastroRepository extends JpaRepository<MotivoDoCadastro, Long> {
}
