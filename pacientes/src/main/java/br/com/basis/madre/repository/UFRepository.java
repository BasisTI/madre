package br.com.basis.madre.repository;

import br.com.basis.madre.domain.UF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UFRepository extends JpaRepository<UF, Long> {
}
