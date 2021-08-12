package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.TelefoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    TelefoneDTO toDto(Telefone telefone);

    @Mapping(source = "pessoaId", target = "pessoa")
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
