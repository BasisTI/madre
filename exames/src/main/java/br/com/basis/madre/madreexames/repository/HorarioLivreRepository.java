package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.HorarioLivre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HorarioLivre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioLivreRepository extends JpaRepository<HorarioLivre, Long> {
}
