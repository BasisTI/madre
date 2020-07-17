package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Municipio;
import br.com.basis.madre.service.dto.MunicipioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Municipio} and its DTO {@link MunicipioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UFMapper.class})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    @Mapping(source = "uf.id", target = "ufId")
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
