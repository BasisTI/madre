package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.CaracteristicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caracteristica} and its DTO {@link CaracteristicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CaracteristicaMapper extends EntityMapper<CaracteristicaDTO, Caracteristica> {


    @Mapping(target = "unidades", ignore = true)
    @Mapping(target = "removeUnidade", ignore = true)
    Caracteristica toEntity(CaracteristicaDTO caracteristicaDTO);

    default Caracteristica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(id);
        return caracteristica;
    }
}
