package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.service.dto.UnidadeInfusaoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface UnidadeInfusaoMapper extends EntityMapper<UnidadeInfusaoDTO, UnidadeInfusao>{
	
	   default UnidadeInfusao fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        UnidadeInfusao unidadeInfusao = new UnidadeInfusao();
	        unidadeInfusao.setId(id);
	        return unidadeInfusao;
	    }

}
