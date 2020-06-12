package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.PreCadastroPaciente;
import br.com.basis.madre.service.dto.PreCadastroPacienteDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface PreCadastroPacienteMapper extends EntityMapper<PreCadastroPacienteDTO, PreCadastroPaciente> {


    default PreCadastroPaciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreCadastroPaciente preCadastroPaciente = new PreCadastroPaciente();
        preCadastroPaciente.setId(id);
        return preCadastroPaciente;
    }
}
