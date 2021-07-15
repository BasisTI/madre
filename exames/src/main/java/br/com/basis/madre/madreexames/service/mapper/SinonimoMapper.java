package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.SinonimoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sinonimo} and its DTO {@link SinonimoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class})
public interface SinonimoMapper extends EntityMapper<SinonimoDTO, Sinonimo> {

    @Mapping(source = "sinonimo.id", target = "sinonimoId")
    @Mapping(source = "sinonimo.nome", target = "sinonimoNome")
    SinonimoDTO toDto(Sinonimo sinonimo);

    @Mapping(source = "sinonimoId", target = "sinonimo")
    Sinonimo toEntity(SinonimoDTO sinonimoDTO);

    default Sinonimo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sinonimo sinonimo = new Sinonimo();
        sinonimo.setId(id);
        return sinonimo;
    }
}
