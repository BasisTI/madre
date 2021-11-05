package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.HorarioExame;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HorarioExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioExameRepository extends JpaRepository<HorarioExame, Long> {
}
