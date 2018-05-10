package br.com.basis.madre.cadastros.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;


/**
 * Spring Data JPA repository for the UnidadeHospitalar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long> {

}
