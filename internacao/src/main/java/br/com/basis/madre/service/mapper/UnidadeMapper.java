package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.UnidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unidade} and its DTO {@link UnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoUnidadeMapper.class, PrescricaoMapper.class, CirurgiaMapper.class})
public interface UnidadeMapper extends EntityMapper<UnidadeDTO, Unidade> {

    @Mapping(source = "unidadePai.id", target = "unidadePaiId")
    @Mapping(source = "tipoUnidade.id", target = "tipoUnidadeId")
    @Mapping(source = "prescricaoEnfermagem.id", target = "prescricaoEnfermagemId")
    @Mapping(source = "prescricaoMedica.id", target = "prescricaoMedicaId")
    @Mapping(source = "cirurgia.id", target = "cirurgiaId")
    UnidadeDTO toDto(Unidade unidade);

    @Mapping(source = "unidadePaiId", target = "unidadePai")
    @Mapping(target = "caracteristicas", ignore = true)
    @Mapping(target = "removeCaracteristica", ignore = true)
    @Mapping(source = "tipoUnidadeId", target = "tipoUnidade")
    @Mapping(source = "prescricaoEnfermagemId", target = "prescricaoEnfermagem")
    @Mapping(source = "prescricaoMedicaId", target = "prescricaoMedica")
    @Mapping(source = "cirurgiaId", target = "cirurgia")
    @Mapping(target = "alas", ignore = true)
    @Mapping(target = "removeAla", ignore = true)
    @Mapping(target = "clinicas", ignore = true)
    @Mapping(target = "removeClinica", ignore = true)
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
