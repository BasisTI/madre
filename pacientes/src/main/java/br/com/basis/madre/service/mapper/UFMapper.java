package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.UF;
import br.com.basis.madre.service.dto.UFDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link UF} and its DTO {@link UFDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UFMapper extends EntityMapper<UFDTO, UF> {



    default UF fromId(Long id) {
        if (id == null) {
            return null;
        }
        UF uF = new UF();
        uF.setId(id);
        return uF;
    }
}
