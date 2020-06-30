package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.CodigoCatmat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CodigoCatmat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodigoCatmatRepository extends JpaRepository<CodigoCatmat, Long> {

}
