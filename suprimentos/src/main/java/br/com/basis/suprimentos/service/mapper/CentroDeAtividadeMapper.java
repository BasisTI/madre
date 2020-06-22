package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroDeAtividade} and its DTO {@link CentroDeAtividadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CentroDeAtividadeMapper extends EntityMapper<CentroDeAtividadeDTO, CentroDeAtividade> {



    default CentroDeAtividade fromId(Long id) {
        if (id == null) {
            return null;
        }
        CentroDeAtividade centroDeAtividade = new CentroDeAtividade();
        centroDeAtividade.setId(id);
        return centroDeAtividade;
    }
}
