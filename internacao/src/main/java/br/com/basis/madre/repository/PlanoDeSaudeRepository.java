package br.com.basis.madre.repository;

import br.com.basis.madre.domain.PlanoDeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoDeSaude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoDeSaudeRepository extends JpaRepository<PlanoDeSaude, Long> {

}
