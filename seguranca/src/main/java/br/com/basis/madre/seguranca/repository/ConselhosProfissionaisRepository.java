package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.ConselhosProfissionais;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConselhosProfissionais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConselhosProfissionaisRepository extends JpaRepository<ConselhosProfissionais, Long> {
}
