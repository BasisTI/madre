package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Especialidade;
import br.com.basis.madre.service.dto.EspecialidadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EspecialidadeMapper extends EntityMapper<EspecialidadeDTO, Especialidade> {

    default Especialidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Especialidade especialidade = new Especialidade();
        especialidade.setId(id);
        return especialidade;
    }

}
