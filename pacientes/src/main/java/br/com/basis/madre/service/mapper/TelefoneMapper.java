package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.TelefoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class, ResponsavelMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "responsavel.id", target = "responsavelId")
    TelefoneDTO toDto(Telefone telefone);

    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "responsavelId", target = "responsavel")
    Telefone toEntity(TelefoneDTO telefoneDTO);

    default Telefone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telefone telefone = new Telefone();
        telefone.setId(id);
        return telefone;
    }
}
