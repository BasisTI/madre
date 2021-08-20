package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.DocumentosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documentos} and its DTO {@link DocumentosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentosMapper extends EntityMapper<DocumentosDTO, Documentos> {



    default Documentos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Documentos documentos = new Documentos();
        documentos.setId(id);
        return documentos;
    }
}
