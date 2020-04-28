package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CID;
import br.com.basis.madre.service.projection.CidParent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CID entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIDRepository extends JpaRepository<CID, Long> {
    @EntityGraph(value = "CID.parent", type = EntityGraphType.LOAD)
    @Override
    <S extends CID> Page<S> findAll(
        Example<S> example,
        Pageable pageable);

    Page<CID> findByParentIsNull(Pageable pageable);
    Page<CID> findByParentId(Long id, Pageable pageable);
}
