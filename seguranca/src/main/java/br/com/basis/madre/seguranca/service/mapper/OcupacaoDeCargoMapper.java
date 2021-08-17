package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.OcupacaoDeCargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OcupacaoDeCargo} and its DTO {@link OcupacaoDeCargoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CargoMapper.class})
public interface OcupacaoDeCargoMapper extends EntityMapper<OcupacaoDeCargoDTO, OcupacaoDeCargo> {

    @Mapping(source = "cargo.id", target = "cargoId")
    OcupacaoDeCargoDTO toDto(OcupacaoDeCargo ocupacaoDeCargo);

    @Mapping(source = "cargoId", target = "cargo")
    OcupacaoDeCargo toEntity(OcupacaoDeCargoDTO ocupacaoDeCargoDTO);

    default OcupacaoDeCargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        OcupacaoDeCargo ocupacaoDeCargo = new OcupacaoDeCargo();
        ocupacaoDeCargo.setId(id);
        return ocupacaoDeCargo;
    }
}
