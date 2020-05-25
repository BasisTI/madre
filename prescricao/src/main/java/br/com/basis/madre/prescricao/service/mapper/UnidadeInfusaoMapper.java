package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.UnidadeInfusaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadeInfusao} and its DTO {@link UnidadeInfusaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadeInfusaoMapper extends EntityMapper<UnidadeInfusaoDTO, UnidadeInfusao> {


    UnidadeInfusao toEntity(UnidadeInfusaoDTO unidadeInfusaoDTO);

    default UnidadeInfusao fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeInfusao unidadeInfusao = new UnidadeInfusao();
        unidadeInfusao.setId(id);
        return unidadeInfusao;
    }
}
