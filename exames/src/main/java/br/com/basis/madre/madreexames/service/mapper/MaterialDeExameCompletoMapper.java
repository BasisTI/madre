package br.com.basis.madre.madreexames.service.mapper;

import br.com.basis.madre.madreexames.domain.MaterialDeExame;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameCompletoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MaterialDeExameMapper.class})
public interface MaterialDeExameCompletoMapper extends EntityMapper<MaterialDeExameCompletoDTO, MaterialDeExame> {

    MaterialDeExame toEntity(MaterialDeExameCompletoDTO materialDeExameCompletoDTO);

    default MaterialDeExame fromId(Long id) {
        if(id == null) {
            return null;
        }
        MaterialDeExame materialDeExame = new MaterialDeExame();
        materialDeExame.setId(id);
        return materialDeExame;
    }
}
