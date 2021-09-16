package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HorarioAgendado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioAgendadoRepository extends JpaRepository<HorarioAgendado, Long> {

    @Query("select new br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO(h.id, h.horaInicio, " +
        "h.horaFim, h.numeroDeHorarios, h.dia) from HorarioAgendado h where h.id = :id")
    HorarioAgendadoDTO buscaPorId(@Param("id") Long id);

}
