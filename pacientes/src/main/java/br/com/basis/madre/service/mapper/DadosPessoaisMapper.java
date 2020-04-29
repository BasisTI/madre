package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.DadosPessoaisDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PacienteMapper.class, Paciente.class})
public interface DadosPessoaisMapper extends EntityMapper<DadosPessoaisDTO, Paciente> {

//    @Mapping(source = "nome", target = "nome");
//    DadosPessoaisDTO toDto (Paciente paciente);
}
