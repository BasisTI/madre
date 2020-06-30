package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Raca;
import br.com.basis.madre.service.dto.RacaDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Raca} and its DTO {@link RacaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RacaMapper extends EntityMapper<RacaDTO, Raca> {



    default Raca fromId(Long id) {
        if (id == null) {
            return null;
        }
        Raca raca = new Raca();
        raca.setId(id);
        return raca;
    }
}
