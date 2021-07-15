package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.TipoAmostra;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoAmostra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAmostraRepository extends JpaRepository<TipoAmostra, Long> {
}
