package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.ExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exame} and its DTO {@link ExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, TipoAmostraMapper.class, GradeDeAgendamentoMapper.class})
public interface ExameMapper extends EntityMapper<ExameDTO, Exame> {

    @Mapping(source = "materialExame.id", target = "materialExameId")
    @Mapping(source = "materialExame.nome", target = "materialExameNome")
    @Mapping(source = "amostraExame.id", target = "amostraExameId")
    @Mapping(source = "amostraExame.nome", target = "amostraExameNome")
    @Mapping(source = "gradeDeAgendamento.id", target = "gradeDeAgendamentoId")
    ExameDTO toDto(Exame exame);

    @Mapping(source = "materialExameId", target = "materialExame")
    @Mapping(source = "amostraExameId", target = "amostraExame")
    @Mapping(source = "gradeDeAgendamentoId", target = "gradeDeAgendamento")
    @Mapping(target = "grupoAgendamentoExames", ignore = true)
    @Mapping(target = "removeGrupoAgendamentoExame", ignore = true)
    Exame toEntity(ExameDTO exameDTO);

    default Exame fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exame exame = new Exame();
        exame.setId(id);
        return exame;
    }
}
