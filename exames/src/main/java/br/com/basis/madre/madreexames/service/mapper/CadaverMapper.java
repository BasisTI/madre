package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.Cadaver;
import br.com.basis.madre.madreexames.service.dto.CadaverDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Cadaver} and its DTO {@link CadaverDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CadaverMapper extends EntityMapper<CadaverDTO, Cadaver> {



    default Cadaver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cadaver cadaver = new Cadaver();
        cadaver.setId(id);
        return cadaver;
    }
}
