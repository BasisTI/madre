package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.DocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documento} and its DTO {@link DocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrgaoEmissorMapper.class, UFMapper.class})
public interface DocumentoMapper extends EntityMapper<DocumentoDTO, Documento> {

    @Mapping(source = "orgaoEmissor.id", target = "orgaoEmissorId")
    @Mapping(source = "uf.id", target = "ufId")
    @Mapping(source = "cnh", target = "cnh")
    DocumentoDTO toDto(Documento documento);

    @Mapping(source = "orgaoEmissorId", target = "orgaoEmissor")
    @Mapping(source = "ufId", target = "uf")
    @Mapping(source = "cnh", target = "cnh")
    Documento toEntity(DocumentoDTO documentoDTO);

    default Documento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(id);
        return documento;
    }
}
