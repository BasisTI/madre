package br.com.basis.madre.repository;

import br.com.basis.madre.domain.DDD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DDD entity.
 */

@Repository
public interface DDDRepository extends JpaRepository<DDD, Long> {

}
