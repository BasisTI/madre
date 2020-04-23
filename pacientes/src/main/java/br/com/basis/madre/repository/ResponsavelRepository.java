package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Responsavel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Responsavel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
}
