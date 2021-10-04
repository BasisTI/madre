package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Dia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaRepository extends JpaRepository<Dia, Long> {
}
