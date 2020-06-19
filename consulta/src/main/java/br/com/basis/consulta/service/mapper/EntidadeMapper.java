package br.com.basis.consulta.service.mapper;

import br.com.basis.consulta.domain.*;
import br.com.basis.consulta.service.dto.EntidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entidade} and its DTO {@link EntidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntidadeMapper extends EntityMapper<EntidadeDTO, Entidade> {



    default Entidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entidade entidade = new Entidade();
        entidade.setId(id);
        return entidade;
    }
}
