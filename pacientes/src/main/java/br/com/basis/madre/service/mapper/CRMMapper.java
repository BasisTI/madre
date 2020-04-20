package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.CRMDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CRM and its DTO CRMDTO.
 */
@Mapper(componentModel = "spring", uses = {SolicitacaoDeInternacaoMapper.class})
public interface CRMMapper extends EntityMapper<CRMDTO, CRM> {

    @Mapping(source = "solicitacaoDeInternacao.id", target = "solicitacaoDeInternacaoId")
    CRMDTO toDto(CRM cRM);

    @Mapping(source = "solicitacaoDeInternacaoId", target = "solicitacaoDeInternacao")
    CRM toEntity(CRMDTO cRMDTO);

    default CRM fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRM cRM = new CRM();
        cRM.setId(id);
        return cRM;
    }
}
