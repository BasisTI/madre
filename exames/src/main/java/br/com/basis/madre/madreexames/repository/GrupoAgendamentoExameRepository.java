package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GrupoAgendamentoExame entity.
 */
@Repository
public interface GrupoAgendamentoExameRepository extends JpaRepository<GrupoAgendamentoExame, Long> {

    @Query(value = "select distinct grupoAgendamentoExame from GrupoAgendamentoExame grupoAgendamentoExame left join fetch grupoAgendamentoExame.exames",
        countQuery = "select count(distinct grupoAgendamentoExame) from GrupoAgendamentoExame grupoAgendamentoExame")
    Page<GrupoAgendamentoExame> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct grupoAgendamentoExame from GrupoAgendamentoExame grupoAgendamentoExame left join fetch grupoAgendamentoExame.exames")
    List<GrupoAgendamentoExame> findAllWithEagerRelationships();

    @Query("select grupoAgendamentoExame from GrupoAgendamentoExame grupoAgendamentoExame left join fetch grupoAgendamentoExame.exames where grupoAgendamentoExame.id =:id")
    Optional<GrupoAgendamentoExame> findOneWithEagerRelationships(@Param("id") Long id);
}
