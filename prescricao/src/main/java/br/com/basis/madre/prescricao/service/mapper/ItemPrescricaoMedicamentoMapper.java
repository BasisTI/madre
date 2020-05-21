package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPrescricaoMedicamento} and its DTO {@link ItemPrescricaoMedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ViasAdministracaoMapper.class, DiluenteMapper.class, UnidadeInfusaoMapper.class, UnidadeDoseMapper.class, PrescricaoMedicamentoMapper.class,
		TipoAprazamentoMapper.class})
public interface ItemPrescricaoMedicamentoMapper extends EntityMapper<ItemPrescricaoMedicamentoDTO, ItemPrescricaoMedicamento> {

    @Mapping(source = "viasAdministracao.id", target = "viasAdministracaoId")
    @Mapping(source = "diluente.id", target = "diluenteId")
    @Mapping(source = "unidadeInfusao.id", target = "unidadeInfusaoId")
    @Mapping(source = "unidadeDose.id", target = "unidadeDoseId")
    @Mapping(source = "tipoAprazamento.id", target = "tipoAprazamentoId")
    ItemPrescricaoMedicamentoDTO toDto(ItemPrescricaoMedicamento itemPrescricaoMedicamento);

    @Mapping(source = "viasAdministracaoId", target = "viasAdministracao")
    @Mapping(source = "diluenteId", target = "diluente")
    @Mapping(source = "unidadeInfusaoId", target = "unidadeInfusao")
    @Mapping(source = "unidadeDoseId", target = "unidadeDose")
    @Mapping(source = "tipoAprazamentoId", target = "tipoAprazamento.id")
    ItemPrescricaoMedicamento toEntity(ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO);

    default ItemPrescricaoMedicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPrescricaoMedicamento itemPrescricaoMedicamento = new ItemPrescricaoMedicamento();
        itemPrescricaoMedicamento.setId(id);
        return itemPrescricaoMedicamento;
    }
}
