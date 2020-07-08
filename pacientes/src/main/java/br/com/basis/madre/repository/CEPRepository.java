package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CEP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the CEP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEPRepository extends JpaRepository<CEP, Long> {
    Page<CEP> findByCepContainsIgnoreCase(String cep, Pageable pageable);

    Optional<CEP> findByCep(String cep);
}
