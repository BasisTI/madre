package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Caracteristica;
import br.com.basis.madre.domain.Unidade;
import br.com.basis.madre.service.dto.FormularioUnidadeDTO;
import br.com.basis.madre.service.dto.UnidadeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Unidade} and its DTO {@link UnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoUnidadeMapper.class, PrescricaoMapper.class, CirurgiaMapper.class})
public interface FormularioUnidadeMapper extends EntityMapper<FormularioUnidadeDTO, Unidade> {

    @Mapping(source = "unidadePai.id", target = "unidadePaiId")
    @Mapping(source = "tipoUnidade.id", target = "tipoUnidadeId")
    FormularioUnidadeDTO toDto(Unidade unidade);

    @Mapping(source = "unidadePaiId", target = "unidadePai")
    @Mapping(target = "caracteristicas", ignore = true)
    @Mapping(target = "removeCaracteristica", ignore = true)
    @Mapping(source = "tipoUnidadeId", target = "tipoUnidade")
    @Mapping(target = "alas", ignore = true)
    @Mapping(target = "removeAla", ignore = true)
    @Mapping(target = "clinicas", ignore = true)
    @Mapping(target = "removeClinica", ignore = true)
    Unidade toEntity(FormularioUnidadeDTO unidadeDTO);

    default Unidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Unidade unidade = new Unidade();
        unidade.setId(id);
        return unidade;
    }
}
