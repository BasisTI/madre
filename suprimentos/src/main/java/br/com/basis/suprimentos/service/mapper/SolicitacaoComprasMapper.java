package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SolicitacaoCompras} and its DTO {@link SolicitacaoComprasDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitacaoComprasMapper extends EntityMapper<SolicitacaoComprasDTO, SolicitacaoCompras> {



    default SolicitacaoCompras fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitacaoCompras solicitacaoCompras = new SolicitacaoCompras();
        solicitacaoCompras.setId(id);
        return solicitacaoCompras;
    }
}
