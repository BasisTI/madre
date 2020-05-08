package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.service.dto.TipoAprazamentoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface TipoAprazamentoMapper extends EntityMapper<TipoAprazamentoDTO, TipoAprazamento>{
	
	   default TipoAprazamento fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        TipoAprazamento tipoAprazamento = new TipoAprazamento();
	        tipoAprazamento.setId(id);
	        return tipoAprazamento;
	    }

}
