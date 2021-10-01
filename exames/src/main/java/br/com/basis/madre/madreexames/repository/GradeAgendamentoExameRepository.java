package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GradeAgendamentoExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeAgendamentoExameRepository extends JpaRepository<GradeAgendamentoExame, Long> {
}
