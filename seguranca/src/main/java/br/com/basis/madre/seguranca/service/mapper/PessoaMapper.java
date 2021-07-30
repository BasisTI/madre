package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentosMapper.class, CargoMapper.class})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {

    @Mapping(source = "documenetos.id", target = "documenetosId")
    @Mapping(source = "documenetos.numeroDaIdentidade", target = "documenetosNumeroDaIdentidade")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "cargo.descricao", target = "cargoDescricao")
    PessoaDTO toDto(Pessoa pessoa);

    @Mapping(source = "documenetosId", target = "documenetos")
    @Mapping(source = "cargoId", target = "cargo")
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "removeEndereco", ignore = true)
    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "removeTelefone", ignore = true)
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
