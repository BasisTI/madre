package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;

@Mapper(componentModel = "spring", uses = {})
public interface DiluenteMapper extends EntityMapper<DiluenteDTO, Diluente>{
	
	   default Diluente fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Diluente diluente = new Diluente();
	        diluente.setId(id);
	        return diluente;
	    }

}
