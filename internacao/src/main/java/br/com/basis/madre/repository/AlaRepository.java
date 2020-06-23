package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Ala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlaRepository extends JpaRepository<Ala, Long> {

}
