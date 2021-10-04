package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GradeAgendamentoExame entity.
 */
@Repository
public interface GradeAgendamentoExameRepository extends JpaRepository<GradeAgendamentoExame, Long> {

    @Query(value = "select distinct gradeAgendamentoExame from GradeAgendamentoExame gradeAgendamentoExame left join fetch gradeAgendamentoExame.dias",
        countQuery = "select count(distinct gradeAgendamentoExame) from GradeAgendamentoExame gradeAgendamentoExame")
    Page<GradeAgendamentoExame> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct gradeAgendamentoExame from GradeAgendamentoExame gradeAgendamentoExame left join fetch gradeAgendamentoExame.dias")
    List<GradeAgendamentoExame> findAllWithEagerRelationships();

    @Query("select gradeAgendamentoExame from GradeAgendamentoExame gradeAgendamentoExame left join fetch gradeAgendamentoExame.dias where gradeAgendamentoExame.id =:id")
    Optional<GradeAgendamentoExame> findOneWithEagerRelationships(@Param("id") Long id);
}
