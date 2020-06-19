package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentoFiscalEntrada} and its DTO {@link DocumentoFiscalEntradaDTO}.
 */
@Mapper(componentModel = "spring", uses = {FornecedorMapper.class})
public interface DocumentoFiscalEntradaMapper extends EntityMapper<DocumentoFiscalEntradaDTO, DocumentoFiscalEntrada> {

    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    DocumentoFiscalEntradaDTO toDto(DocumentoFiscalEntrada documentoFiscalEntrada);

    @Mapping(source = "fornecedorId", target = "fornecedor")
    DocumentoFiscalEntrada toEntity(DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO);

    default DocumentoFiscalEntrada fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentoFiscalEntrada documentoFiscalEntrada = new DocumentoFiscalEntrada();
        documentoFiscalEntrada.setId(id);
        return documentoFiscalEntrada;
    }
}
