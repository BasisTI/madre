package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.service.dto.CEPDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CEP} and its DTO {@link CEPDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CEPMapper extends EntityMapper<CEPDTO, CEP> {



    default CEP fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEP cEP = new CEP();
        cEP.setId(id);
        return cEP;
    }
}
