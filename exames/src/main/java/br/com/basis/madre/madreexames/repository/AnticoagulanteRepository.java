package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Anticoagulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Anticoagulante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnticoagulanteRepository extends JpaRepository<Anticoagulante, Long> {
}
