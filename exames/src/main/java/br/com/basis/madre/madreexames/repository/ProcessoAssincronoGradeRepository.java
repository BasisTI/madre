package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data  repository for the ProcessoAssincronoGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessoAssincronoGradeRepository extends JpaRepository<ProcessoAssincronoGrade, String> {
}
