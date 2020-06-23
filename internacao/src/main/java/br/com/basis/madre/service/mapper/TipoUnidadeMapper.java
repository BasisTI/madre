package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.TipoUnidade;
import br.com.basis.madre.service.dto.TipoUnidadeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link TipoUnidade} and its DTO {@link TipoUnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoUnidadeMapper extends EntityMapper<TipoUnidadeDTO, TipoUnidade> {



    default TipoUnidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoUnidade tipoUnidade = new TipoUnidade();
        tipoUnidade.setId(id);
        return tipoUnidade;
    }
}
