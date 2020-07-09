package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.service.dto.CEPDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CEP} and its DTO {@link CEPDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, UFMapper.class})
public interface CEPMapper extends EntityMapper<CEPDTO, CEP> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "uf.id", target = "ufId")
    CEPDTO toDto(CEP cep);

    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "ufId", target = "uf")
    CEP toEntity(CEPDTO cepdto);

    default CEP fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEP cEP = new CEP();
        cEP.setId(id);
        return cEP;
    }
}
