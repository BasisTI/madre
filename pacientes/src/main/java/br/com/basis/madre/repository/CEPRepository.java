package br.com.basis.madre.repository;
import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.service.projection.MunicipioUF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CEP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEPRepository extends JpaRepository<CEP, Long> {
    Page<CEP> findByCepContainsIgnoreCase(String cep, Pageable pageable);
}
