package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Recipiente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recipiente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipienteRepository extends JpaRepository<Recipiente, Long> {
}
