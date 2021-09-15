package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame;
import br.com.basis.madre.madreexames.service.dto.GrupoAgendamentoExameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link GrupoAgendamentoExame} and its DTO {@link GrupoAgendamentoExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class})
public interface GrupoAgendamentoExameMapper extends EntityMapper<GrupoAgendamentoExameDTO, GrupoAgendamentoExame> {


    @Mapping(target = "removeExame", ignore = true)

    default GrupoAgendamentoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoAgendamentoExame grupoAgendamentoExame = new GrupoAgendamentoExame();
        grupoAgendamentoExame.setId(id);
        return grupoAgendamentoExame;
    }
}
