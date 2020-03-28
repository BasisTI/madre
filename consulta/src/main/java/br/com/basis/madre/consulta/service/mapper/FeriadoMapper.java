package br.com.basis.madre.consulta.service.mapper;

import br.com.basis.madre.consulta.domain.Feriado;
import br.com.basis.madre.consulta.service.dto.FeriadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FeriadoMapper {

    Feriado toEntity(FeriadoDTO dto);

    FeriadoDTO toDto(Feriado entity);
}
