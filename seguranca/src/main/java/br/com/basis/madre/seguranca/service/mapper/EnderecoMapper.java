package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, PessoaMapper.class})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.nome", target = "municipioNome")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    EnderecoDTO toDto(Endereco endereco);

    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "pessoaId", target = "pessoa")
    Endereco toEntity(EnderecoDTO enderecoDTO);

    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
