package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.ServidorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servidor} and its DTO {@link ServidorDTO}.
 */
@Mapper(componentModel = "spring", uses = {VinculoMapper.class, PessoaMapper.class, RamalMapper.class, UsuarioMapper.class})
public interface ServidorMapper extends EntityMapper<ServidorDTO, Servidor> {

    @Mapping(source = "vinculo.id", target = "vinculoId")
    @Mapping(source = "vinculo.descricao", target = "vinculoDescricao")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "ramal.id", target = "ramalId")
    @Mapping(source = "ramal.numero", target = "ramalNumero")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    ServidorDTO toDto(Servidor servidor);

    @Mapping(source = "vinculoId", target = "vinculo")
    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(source = "ramalId", target = "ramal")
    @Mapping(target = "grupofuncionals", ignore = true)
    @Mapping(target = "removeGrupofuncional", ignore = true)
    @Mapping(source = "usuarioId", target = "usuario")
    Servidor toEntity(ServidorDTO servidorDTO);

    default Servidor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Servidor servidor = new Servidor();
        servidor.setId(id);
        return servidor;
    }
}
