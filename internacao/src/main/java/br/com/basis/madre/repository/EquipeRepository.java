package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Equipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
