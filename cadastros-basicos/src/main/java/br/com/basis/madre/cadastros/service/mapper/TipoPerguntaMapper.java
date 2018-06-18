package br.com.basis.madre.cadastros.service.mapper;

import br.com.basis.madre.cadastros.domain.*;
import br.com.basis.madre.cadastros.service.dto.TipoPerguntaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoPergunta and its DTO TipoPerguntaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoPerguntaMapper extends EntityMapper<TipoPerguntaDTO, TipoPergunta> {



    default TipoPergunta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoPergunta tipoPergunta = new TipoPergunta();
        tipoPergunta.setId(id);
        return tipoPergunta;
    }
}
