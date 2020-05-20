package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.ApresentacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Apresentacao} and its DTO {@link ApresentacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApresentacaoMapper extends EntityMapper<ApresentacaoDTO, Apresentacao> {



    default Apresentacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Apresentacao apresentacao = new Apresentacao();
        apresentacao.setId(id);
        return apresentacao;
    }
}
