package br.com.basis.madre.repository;

import br.com.basis.madre.domain.EnderecoCEP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the EnderecoCEP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEPRepository extends JpaRepository<EnderecoCEP, Long> {
    Optional<EnderecoCEP> findByCep(String cep);

    Page<EnderecoCEP> findByCepContainsIgnoreCase(String cep, Pageable pageable);
}
