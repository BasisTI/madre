package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;


@Mapper(componentModel = "spring", uses= {ViasAdministracao.class, Diluente.class, UnidadeInfusao.class, UnidadeDose.class,
		PrescricaoMedicamento.class, TipoAprazamento.class})
public interface ItemPrescricaoMedicamentoMapper extends EntityMapper<ItemPrescricaoMedicamentoDTO, ItemPrescricaoMedicamento> {
	
	@Mapping(source = "viasAdministracao.id", target = "viasAdministracaoId")
	@Mapping(source = "diluente.id", target = "diluenteId")
	@Mapping(source = "unidadeInfusao.id", target = "unidadeInfusaoId")
	@Mapping(source = "unidadeDose.id", target = "unidadeDoseId")
	@Mapping(source = "prescricaoMedicamento.id", target = "prescricaoMedicamentoId")
	@Mapping(source = "tipoAprazamento.id", target = "tipoAprazamentoId")
	ItemPrescricaoMedicamentoDTO toDto(ItemPrescricaoMedicamento itemPrescricaoMedicamento);
	
	  default ItemPrescricaoMedicamento fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ItemPrescricaoMedicamento itemPrescricaoMedicamento = new ItemPrescricaoMedicamento();
	        itemPrescricaoMedicamento.setId(id);
	        return itemPrescricaoMedicamento;
	    }

}
