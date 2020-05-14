package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CID;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CID entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIDRepository extends JpaRepository<CID, Long> {

    @Override
    @EntityGraph(value = "CID.pai", type = EntityGraphType.LOAD)
    List<CID> findAll(Sort sort);

    Page<CID> findByPaiNullAndDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

    List<CID> findByPaiNull(Sort sort);

    List<CID> findByPaiId(Long id, Sort sort);

}
