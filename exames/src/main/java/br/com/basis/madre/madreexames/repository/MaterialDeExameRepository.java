package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.MaterialDeExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialDeExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialDeExameRepository extends JpaRepository<MaterialDeExame, Long> {
}
