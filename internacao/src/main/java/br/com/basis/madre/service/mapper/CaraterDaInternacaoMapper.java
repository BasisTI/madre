package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.CaraterDaInternacao;
import br.com.basis.madre.service.dto.CaraterDaInternacaoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CaraterDaInternacao} and its DTO {@link CaraterDaInternacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CaraterDaInternacaoMapper extends
    EntityMapper<CaraterDaInternacaoDTO, CaraterDaInternacao> {

    default CaraterDaInternacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        CaraterDaInternacao caraterDaInternacao = new CaraterDaInternacao();
        caraterDaInternacao.setId(id);
        return caraterDaInternacao;
    }

}
