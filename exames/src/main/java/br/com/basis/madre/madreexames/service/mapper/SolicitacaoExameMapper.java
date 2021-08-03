package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SolicitacaoExame} and its DTO {@link SolicitacaoExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitacaoExameMapper extends EntityMapper<SolicitacaoExameDTO, SolicitacaoExame> {


    @Mapping(target = "solicitacaoExames", ignore = true)
    @Mapping(target = "removeSolicitacaoExame", ignore = true)
    SolicitacaoExame toEntity(SolicitacaoExameDTO solicitacaoExameDTO);

    default SolicitacaoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitacaoExame solicitacaoExame = new SolicitacaoExame();
        solicitacaoExame.setId(id);
        return solicitacaoExame;
    }
}
