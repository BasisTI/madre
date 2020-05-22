package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.CirurgiasLeitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CirurgiasLeito} and its DTO {@link CirurgiasLeitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CirurgiasLeitoMapper extends EntityMapper<CirurgiasLeitoDTO, CirurgiasLeito> {



    default CirurgiasLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        CirurgiasLeito cirurgiasLeito = new CirurgiasLeito();
        cirurgiasLeito.setId(id);
        return cirurgiasLeito;
    }
}
