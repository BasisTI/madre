package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.Recipiente;
import br.com.basis.madre.madreexames.service.dto.RecipienteDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Recipiente} and its DTO {@link RecipienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipienteMapper extends EntityMapper<RecipienteDTO, Recipiente> {



    default Recipiente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipiente recipiente = new Recipiente();
        recipiente.setId(id);
        return recipiente;
    }
}
