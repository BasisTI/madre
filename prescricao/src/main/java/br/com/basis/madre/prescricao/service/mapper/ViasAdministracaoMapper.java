package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.service.dto.ViasAdministracaoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ViasAdministracaoMapper extends EntityMapper<ViasAdministracaoDTO, ViasAdministracao>{
	
	   default ViasAdministracao fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ViasAdministracao viasAdministracao = new ViasAdministracao();
	        viasAdministracao.setId(id);
	        return viasAdministracao;
	    }

}
