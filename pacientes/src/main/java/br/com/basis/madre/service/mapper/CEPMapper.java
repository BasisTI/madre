package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.EnderecoCEP;
import br.com.basis.madre.service.dto.CEPDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EnderecoCEP} and its DTO {@link CEPDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, UFMapper.class})
public interface CEPMapper extends EntityMapper<CEPDTO, EnderecoCEP> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "uf.id", target = "ufId")
    CEPDTO toDto(EnderecoCEP cep);

    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "ufId", target = "uf")
    EnderecoCEP toEntity(CEPDTO cepdto);

    default EnderecoCEP fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnderecoCEP cEP = new EnderecoCEP();
        cEP.setId(id);
        return cEP;
    }
}
