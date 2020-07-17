package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPrescricaoMedicamento} and its DTO {@link ItemPrescricaoMedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ViasAdministracaoMapper.class, DiluenteMapper.class, UnidadeInfusaoMapper.class, UnidadeDoseMapper.class,
		TipoAprazamentoMapper.class, MedicamentoMapper.class})
public interface ItemPrescricaoMedicamentoMapper extends EntityMapper<ItemPrescricaoMedicamentoDTO, ItemPrescricaoMedicamento> {

    @Mapping(source = "viasAdministracao", target = "viasAdministracao")
    @Mapping(source = "diluente", target = "diluente")
    @Mapping(source = "unidadeInfusao", target = "unidadeInfusao")
    @Mapping(source = "unidadeDose", target = "unidadeDose")
    @Mapping(source = "tipoAprazamento", target = "tipoAprazamento")
    @Mapping(source = "medicamento", target="medicamento")
    ItemPrescricaoMedicamentoDTO toDto(ItemPrescricaoMedicamento itemPrescricaoMedicamento);

    @Mapping(source = "viasAdministracao", target = "viasAdministracao")
    @Mapping(source = "diluente", target = "diluente")
    @Mapping(source = "unidadeInfusao", target = "unidadeInfusao")
    @Mapping(source = "unidadeDose", target = "unidadeDose")
    @Mapping(source = "tipoAprazamento", target = "tipoAprazamento")
    @Mapping(source = "medicamento", target="medicamento")
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
