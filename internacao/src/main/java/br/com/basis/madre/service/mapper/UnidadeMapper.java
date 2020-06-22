package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.UnidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unidade} and its DTO {@link UnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlaMapper.class, ClinicaMapper.class, TipoUnidadeMapper.class, PrescricaoMapper.class, CirurgiaMapper.class, CaracteristicaMapper.class})
public interface UnidadeMapper extends EntityMapper<UnidadeDTO, Unidade> {

    @Mapping(source = "unidadePai.id", target = "unidadePaiId")
    @Mapping(source = "ala.id", target = "alaId")
    @Mapping(source = "clinica.id", target = "clinicaId")
    @Mapping(source = "tipoUnidade.id", target = "tipoUnidadeId")
    @Mapping(source = "prescricaoEnfermagem.id", target = "prescricaoEnfermagemId")
    @Mapping(source = "prescricaoMedica.id", target = "prescricaoMedicaId")
    @Mapping(source = "cirurgia.id", target = "cirurgiaId")
    UnidadeDTO toDto(Unidade unidade);

    @Mapping(source = "unidadePaiId", target = "unidadePai")
    @Mapping(source = "alaId", target = "ala")
    @Mapping(source = "clinicaId", target = "clinica")
    @Mapping(source = "tipoUnidadeId", target = "tipoUnidade")
    @Mapping(source = "prescricaoEnfermagemId", target = "prescricaoEnfermagem")
    @Mapping(source = "prescricaoMedicaId", target = "prescricaoMedica")
    @Mapping(source = "cirurgiaId", target = "cirurgia")
    @Mapping(target = "removeCaracteristica", ignore = true)
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
