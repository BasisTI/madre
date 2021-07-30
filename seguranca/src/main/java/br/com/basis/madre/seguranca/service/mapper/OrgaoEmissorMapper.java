package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.OrgaoEmissorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrgaoEmissor} and its DTO {@link OrgaoEmissorDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentosMapper.class})
public interface OrgaoEmissorMapper extends EntityMapper<OrgaoEmissorDTO, OrgaoEmissor> {

    @Mapping(source = "documentos.id", target = "documentosId")
    @Mapping(source = "documentos.numeroDaIdentidade", target = "documentosNumeroDaIdentidade")
    OrgaoEmissorDTO toDto(OrgaoEmissor orgaoEmissor);

    @Mapping(source = "documentosId", target = "documentos")
    OrgaoEmissor toEntity(OrgaoEmissorDTO orgaoEmissorDTO);

    default OrgaoEmissor fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrgaoEmissor orgaoEmissor = new OrgaoEmissor();
        orgaoEmissor.setId(id);
        return orgaoEmissor;
    }
}
