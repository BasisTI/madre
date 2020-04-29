package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CRM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CRM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRMRepository extends JpaRepository<CRM, Long> {

}
