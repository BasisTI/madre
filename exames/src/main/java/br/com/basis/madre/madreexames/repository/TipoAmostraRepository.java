package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.TipoAmostra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoAmostra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAmostraRepository extends JpaRepository<TipoAmostra, Long> {
}
