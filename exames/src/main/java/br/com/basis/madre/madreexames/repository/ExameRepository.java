package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Exame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Exame entity.
 */
@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    @Query(value = "select distinct exame from Exame exame left join fetch exame.exames",
        countQuery = "select count(distinct exame) from Exame exame")
    Page<Exame> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct exame from Exame exame left join fetch exame.exames")
    List<Exame> findAllWithEagerRelationships();

    @Query("select exame from Exame exame left join fetch exame.exames where exame.id =:id")
    Optional<Exame> findOneWithEagerRelationships(@Param("id") Long id);
}
