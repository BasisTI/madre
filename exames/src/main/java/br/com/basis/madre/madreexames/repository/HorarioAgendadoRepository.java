package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HorarioAgendado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioAgendadoRepository extends JpaRepository<HorarioAgendado, Long> {
}
