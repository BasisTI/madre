package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CaracteristicaArmazenamentoMapper extends EntityMapper<CaracteristicaArmazenamentoDTO, CaracteristicaArmazenamento> {
    @Mapping(target = "composicoes", ignore = true)
    CaracteristicaArmazenamento toEntity(CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO);

    default CaracteristicaArmazenamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        CaracteristicaArmazenamento caracteristicaArmazenamento = new CaracteristicaArmazenamento();
        caracteristicaArmazenamento.setId(id);
        return caracteristicaArmazenamento;
    }
}
