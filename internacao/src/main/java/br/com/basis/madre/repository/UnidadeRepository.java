package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Unidade entity.
 */
@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query(value = "select distinct unidade from Unidade unidade left join fetch unidade.caracteristicas",
        countQuery = "select count(distinct unidade) from Unidade unidade")
    Page<Unidade> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct unidade from Unidade unidade left join fetch unidade.caracteristicas")
    List<Unidade> findAllWithEagerRelationships();

    @Query("select unidade from Unidade unidade left join fetch unidade.caracteristicas where unidade.id =:id")
    Optional<Unidade> findOneWithEagerRelationships(@Param("id") Long id);

}
