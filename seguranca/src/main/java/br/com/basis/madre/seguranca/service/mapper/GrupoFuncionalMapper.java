package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.GrupoFuncionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoFuncional} and its DTO {@link GrupoFuncionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServidorMapper.class})
public interface GrupoFuncionalMapper extends EntityMapper<GrupoFuncionalDTO, GrupoFuncional> {

    @Mapping(source = "servidor.id", target = "servidorId")
    GrupoFuncionalDTO toDto(GrupoFuncional grupoFuncional);

    @Mapping(source = "servidorId", target = "servidor")
    GrupoFuncional toEntity(GrupoFuncionalDTO grupoFuncionalDTO);

    default GrupoFuncional fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoFuncional grupoFuncional = new GrupoFuncional();
        grupoFuncional.setId(id);
        return grupoFuncional;
    }
}
