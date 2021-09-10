package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.GradeDeAgendamento;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GradeDeAgendamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeDeAgendamentoRepository extends JpaRepository<GradeDeAgendamento, Long> {

    @Query("select new br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO(g.id, g.unidadeExecutoraId, " +
        "g.responsavelId, g.ativo, e.id, e.nome,gp.id, gp.nome, s.id, s.identificacaoDaSala) " +
        "from GradeDeAgendamento g join g.exameGrade e join g.salaGrade s join g.grupoGrade gp where g.id = :id")
    GradeDeAgendamentoDTO buscaPorId(@Param("id") Long id);

}
