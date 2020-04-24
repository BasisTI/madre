package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.OrgaoEmissorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrgaoEmissor} and its DTO {@link OrgaoEmissorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrgaoEmissorMapper extends EntityMapper<OrgaoEmissorDTO, OrgaoEmissor> {



    default OrgaoEmissor fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrgaoEmissor orgaoEmissor = new OrgaoEmissor();
        orgaoEmissor.setId(id);
        return orgaoEmissor;
    }
}
