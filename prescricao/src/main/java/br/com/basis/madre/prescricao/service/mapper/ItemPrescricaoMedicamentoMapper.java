package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;


@Mapper(componentModel = "spring", uses= {ViasAdministracaoMapper.class, DiluenteMapper.class, UnidadeInfusaoMapper.class, UnidadeDoseMapper.class
		, TipoAprazamentoMapper.class})
public interface ItemPrescricaoMedicamentoMapper extends EntityMapper<ItemPrescricaoMedicamentoDTO, ItemPrescricaoMedicamento> {
	
	@Mapping(source = "viasAdministracao.id", target = "viasAdministracaoId")
	@Mapping(source = "diluente.id", target = "diluenteId")
	@Mapping(source = "unidadeInfusao.id", target = "unidadeInfusaoId")
	@Mapping(source = "unidadeDose.id", target = "unidadeDoseId")
	@Mapping(source = "tipoAprazamento.id", target = "tipoAprazamentoId")
	
	ItemPrescricaoMedicamentoDTO toDto(ItemPrescricaoMedicamento itemPrescricaoMedicamento);
	
	
	@Mapping(source = "viasAdministracaoId", target = "viasAdministracao.id")
	@Mapping(source = "diluenteId", target = "diluente.id")
	@Mapping(source = "unidadeInfusaoId", target = "unidadeInfusao.id")
	@Mapping(source = "unidadeDoseId", target = "unidadeDose.id")
	@Mapping(source = "tipoAprazamentoId", target = "tipoAprazamento.id")
	ItemPrescricaoMedicamento toEntity(ItemPrescricaoMedicamentoDTO dto);
	
	
	  default ItemPrescricaoMedicamento fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ItemPrescricaoMedicamento itemPrescricaoMedicamento = new ItemPrescricaoMedicamento();
	        itemPrescricaoMedicamento.setId(id);
	        return itemPrescricaoMedicamento;
	    }

}
