package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.Cadaver;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cadaver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadaverRepository extends JpaRepository<Cadaver, Long> {
}
