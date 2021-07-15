package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.ControleQualidade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ControleQualidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleQualidadeRepository extends JpaRepository<ControleQualidade, Long> {
}
