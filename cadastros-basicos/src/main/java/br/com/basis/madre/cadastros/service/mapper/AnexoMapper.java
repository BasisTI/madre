package br.com.basis.madre.cadastros.service.mapper;

import br.com.basis.madre.cadastros.domain.*;
import br.com.basis.madre.cadastros.service.dto.AnexoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Anexo and its DTO AnexoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {



    default Anexo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anexo anexo = new Anexo();
        anexo.setId(id);
        return anexo;
    }
}
