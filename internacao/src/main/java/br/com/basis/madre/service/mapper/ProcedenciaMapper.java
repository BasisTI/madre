package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Procedencia;
import br.com.basis.madre.service.dto.ProcedenciaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProcedenciaMapper extends EntityMapper<ProcedenciaDTO, Procedencia> {

    default Procedencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Procedencia procedencia = new Procedencia();
        procedencia.setId(id);
        return procedencia;
    }

}
