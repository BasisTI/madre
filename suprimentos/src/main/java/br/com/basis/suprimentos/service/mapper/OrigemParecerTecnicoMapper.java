package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrigemParecerTecnicoMapper extends EntityMapper<OrigemParecerTecnicoDTO, OrigemParecerTecnico> {
    default OrigemParecerTecnico fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrigemParecerTecnico origemParecerTecnico = new OrigemParecerTecnico();
        origemParecerTecnico.setId(id);
        return origemParecerTecnico;
    }
}
