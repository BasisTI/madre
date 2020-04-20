package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.EspecialidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Especialidade and its DTO EspecialidadeDTO.
 */
@Mapper(componentModel = "spring", uses = {EquipeMapper.class})
public interface EspecialidadeMapper extends EntityMapper<EspecialidadeDTO, Especialidade> {

    @Mapping(source = "equipe.id", target = "equipeId")
    EspecialidadeDTO toDto(Especialidade especialidade);

    @Mapping(source = "equipeId", target = "equipe")
    Especialidade toEntity(EspecialidadeDTO especialidadeDTO);

    default Especialidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Especialidade especialidade = new Especialidade();
        especialidade.setId(id);
        return especialidade;
    }
}
