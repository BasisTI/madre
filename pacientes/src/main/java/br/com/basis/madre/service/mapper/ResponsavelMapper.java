package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.ResponsavelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Responsavel} and its DTO {@link ResponsavelDTO}.
 */
@Mapper(componentModel = "spring", uses = {TelefoneMapper.class, GrauDeParentescoMapper.class})
public interface ResponsavelMapper extends EntityMapper<ResponsavelDTO, Responsavel> {

    @Mapping(source = "grauDeParentesco.id", target = "grauDeParentescoId")
    ResponsavelDTO toDto(Responsavel responsavel);


    @Mapping(source = "telefone", target = "telefones")
    @Mapping(source = "grauDeParentescoId", target = "grauDeParentesco")
    Responsavel toEntity(ResponsavelDTO responsavelDTO);

    default Responsavel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Responsavel responsavel = new Responsavel();
        responsavel.setId(id);
        return responsavel;
    }
}
