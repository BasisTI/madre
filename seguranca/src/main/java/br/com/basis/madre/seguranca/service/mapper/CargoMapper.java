package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.CargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cargo} and its DTO {@link CargoDTO}.
 */
@Mapper(componentModel = "spring", uses = {OcupacaoDeCargoMapper.class})
public interface CargoMapper extends EntityMapper<CargoDTO, Cargo> {

    @Mapping(source = "ocupacaoDeCargo.id", target = "ocupacaoDeCargoId")
    CargoDTO toDto(Cargo cargo);

    @Mapping(source = "ocupacaoDeCargoId", target = "ocupacaoDeCargo")
    Cargo toEntity(CargoDTO cargoDTO);

    default Cargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cargo cargo = new Cargo();
        cargo.setId(id);
        return cargo;
    }
}
