package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the TaUsuarioUnidadeHospitalar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaUsuarioUnidadeHospitalarRepository extends JpaRepository<TaUsuarioUnidadeHospitalar, Long> {

    List<TaUsuarioUnidadeHospitalar> findByUnidadeHospitalarId(Long id);

    @Query(value="SELECT unidadeHospitalarId FROM TaUsuarioUnidadeHospitalar")
    Collection<TaUsuarioUnidadeHospitalar> buscarId();

    @Query(value = "SELECT unidadeHospitalarId FROM TaUsuarioUnidadeHospitalar WHERE unidadeHospitalarId = :#{#_id} ")
    Collection<TaUsuarioUnidadeHospitalar> buscarUmId(@Param("_id")  Long id);

}
