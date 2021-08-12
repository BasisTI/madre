package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.CargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cargo} and its DTO {@link CargoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CargoMapper extends EntityMapper<CargoDTO, Cargo> {


    @Mapping(target = "ocupacaoDeCargos", ignore = true)
    @Mapping(target = "removeOcupacaoDeCargo", ignore = true)
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
