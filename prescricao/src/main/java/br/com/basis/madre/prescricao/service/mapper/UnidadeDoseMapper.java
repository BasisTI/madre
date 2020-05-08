package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.service.dto.UnidadeDoseDTO;

@Mapper(componentModel = "spring", uses = {})
public interface UnidadeDoseMapper extends EntityMapper<UnidadeDoseDTO, UnidadeDose>{
	
	   default UnidadeDose fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        UnidadeDose unidadeDose = new UnidadeDose();
	        unidadeDose.setId(id);
	        return unidadeDose;
	    }

}
