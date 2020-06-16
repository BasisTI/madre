package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.UnidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unidade} and its DTO {@link UnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoUnidadeMapper.class})
public interface UnidadeMapper extends EntityMapper<UnidadeDTO, Unidade> {

    @Mapping(source = "unidadePai.id", target = "unidadePaiId")
    @Mapping(source = "tipoUnidade.id", target = "tipoUnidadeId")
    UnidadeDTO toDto(Unidade unidade);

    @Mapping(source = "unidadePaiId", target = "unidadePai")
    @Mapping(target = "caracteristicas", ignore = true)
    @Mapping(target = "removeCaracteristica", ignore = true)
    @Mapping(source = "tipoUnidadeId", target = "tipoUnidade")
    Unidade toEntity(UnidadeDTO unidadeDTO);

    default Unidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Unidade unidade = new Unidade();
        unidade.setId(id);
        return unidade;
    }
}
