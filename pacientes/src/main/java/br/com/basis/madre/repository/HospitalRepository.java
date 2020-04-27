package br.com.basis.madre.repository;
import br.com.basis.madre.domain.Hospital;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hospital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
