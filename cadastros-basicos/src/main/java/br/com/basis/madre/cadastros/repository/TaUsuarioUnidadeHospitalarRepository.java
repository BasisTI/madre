package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TaUsuarioUnidadeHospitalar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaUsuarioUnidadeHospitalarRepository extends JpaRepository<TaUsuarioUnidadeHospitalar, Long> {

}
