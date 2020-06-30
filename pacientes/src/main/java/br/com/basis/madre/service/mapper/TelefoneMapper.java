package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Telefone;
import br.com.basis.madre.service.dto.TelefoneDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class, ResponsavelMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {
    TelefoneDTO toDto(Telefone telefone);
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
