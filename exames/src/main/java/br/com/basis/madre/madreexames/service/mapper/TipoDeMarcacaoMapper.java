package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.TipoDeMarcacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDeMarcacao} and its DTO {@link TipoDeMarcacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeMarcacaoMapper extends EntityMapper<TipoDeMarcacaoDTO, TipoDeMarcacao> {



    default TipoDeMarcacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeMarcacao tipoDeMarcacao = new TipoDeMarcacao();
        tipoDeMarcacao.setId(id);
        return tipoDeMarcacao;
    }
}
