package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Cadaver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cadaver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadaverRepository extends JpaRepository<Cadaver, Long> {
}
