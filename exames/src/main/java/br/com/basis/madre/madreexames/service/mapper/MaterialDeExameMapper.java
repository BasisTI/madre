package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MaterialDeExame} and its DTO {@link MaterialDeExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class})
public interface MaterialDeExameMapper extends EntityMapper<MaterialDeExameDTO, MaterialDeExame> {

    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "material.nome", target = "materialNome")
    MaterialDeExameDTO toDto(MaterialDeExame materialDeExame);

    @Mapping(source = "materialId", target = "material")
    MaterialDeExame toEntity(MaterialDeExameDTO materialDeExameDTO);

    default MaterialDeExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        MaterialDeExame materialDeExame = new MaterialDeExame();
        materialDeExame.setId(id);
        return materialDeExame;
    }
}
