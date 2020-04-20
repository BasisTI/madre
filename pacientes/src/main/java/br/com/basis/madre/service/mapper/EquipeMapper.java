package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.EquipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Equipe and its DTO EquipeDTO.
 */
@Mapper(componentModel = "spring", uses = {SolicitacaoDeInternacaoMapper.class})
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {

    @Mapping(source = "solicitacaoDeInternacao.id", target = "solicitacaoDeInternacaoId")
    EquipeDTO toDto(Equipe equipe);

    @Mapping(source = "solicitacaoDeInternacaoId", target = "solicitacaoDeInternacao")
    @Mapping(target = "especialidades", ignore = true)
    Equipe toEntity(EquipeDTO equipeDTO);

    default Equipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipe equipe = new Equipe();
        equipe.setId(id);
        return equipe;
    }
}
