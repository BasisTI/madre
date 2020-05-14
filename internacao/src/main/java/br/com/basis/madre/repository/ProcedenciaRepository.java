package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Procedencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Procedencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedenciaRepository extends JpaRepository<Procedencia, Long> {

}
