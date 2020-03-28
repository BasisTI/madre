package br.com.basis.madre.consulta.repository;

import br.com.basis.madre.consulta.domain.Feriado;
import br.com.basis.madre.consulta.service.dto.FeriadoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeriadoRepository extends JpaRepository<Feriado, Long> , JpaSpecificationExecutor<Feriado> {

    @Query("SELECT new br.com.basis.madre.consulta.service.dto.FeriadoDTO(f.id, f.data, f.diaSemana, f.turno, f.situacao) FROM Feriado f" +
        " WHERE (:#{#filtro.data} IS NULL OR TRUNC(f.data) = (TO_DATE(:#{#filtro.data}, 'DD/MM/YYYY')))" +
        " AND (:#{#filtro.diaSemana} IS NULL OR LOWER(f.diaSemana) LIKE LOWER(CONCAT(CONCAT('%', :#{#filtro.diaSemana}, '%'))))" +
        " AND (:#{#filtro.turno} IS NULL OR LOWER(f.turno) LIKE LOWER(CONCAT(CONCAT('%', :#{#filtro.turno}, '%'))))" +
        " AND (:#{#filtro.situacao} IS NULL OR f.situacao = :#{#filtro.situacao})")
    Page<FeriadoDTO> findAllByFilter(@Param("filtro") FeriadoDTO feriadoDTO, Pageable pageable);
}
