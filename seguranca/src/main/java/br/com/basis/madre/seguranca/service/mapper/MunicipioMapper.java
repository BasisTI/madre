package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Municipio} and its DTO {@link MunicipioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UFMapper.class})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    @Mapping(source = "uf.id", target = "ufId")
    @Mapping(source = "uf.unidadeFederativa", target = "ufUnidadeFederativa")
    MunicipioDTO toDto(Municipio municipio);

    @Mapping(source = "ufId", target = "uf")
    Municipio toEntity(MunicipioDTO municipioDTO);

    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
