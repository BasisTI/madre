package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Ala;
import br.com.basis.madre.service.dto.AlaDTO;
import org.mapstruct.Mapper;


/**
 * Mapper for the entity {@link Ala} and its DTO {@link AlaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlaMapper extends EntityMapper<AlaDTO, Ala> {



    default Ala fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ala ala = new Ala();
        ala.setId(id);
        return ala;
    }
}
