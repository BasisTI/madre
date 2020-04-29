package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CID entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIDRepository extends JpaRepository<CID, Long> {

}
