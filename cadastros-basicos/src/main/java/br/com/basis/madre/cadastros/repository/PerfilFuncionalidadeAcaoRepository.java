package br.com.basis.madre.cadastros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.PerfilFuncionalidadeAcao;




/**
 * Spring Data JPA repository for the PerfilFuncionalidadeAcao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilFuncionalidadeAcaoRepository extends JpaRepository<PerfilFuncionalidadeAcao, Long> {
    @Modifying
    @Query(value = "DELETE FROM PerfilFuncionalidadeAcao WHERE iderfil = :#{#idPerfil}")
    void apagar(@Param("idPerfil") Long idPerfil);
}
