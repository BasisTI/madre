package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Procedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

}
