package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.Servidor;
import br.com.basis.madre.seguranca.service.dto.ServidorDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Servidor} and its DTO {@link ServidorDTO}.
 */
@Mapper(componentModel = "spring", uses = {VinculoMapper.class, PessoaMapper.class, UsuarioMapper.class, RamalMapper.class})
public interface ServidorMapper extends EntityMapper<ServidorDTO, Servidor> {

    @Mapping(source = "vinculo.id", target = "vinculoId")
    @Mapping(source = "vinculo.descricao", target = "vinculoDescricao")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.codigo", target = "pessoaCodigo")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    @Mapping(source = "ramal.id", target = "ramalId")
    ServidorDTO toDto(Servidor servidor);

    @Mapping(source = "vinculoId", target = "vinculo")
    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "grupofuncionals", ignore = true)
    @Mapping(target = "removeGrupofuncional", ignore = true)
    @Mapping(target = "graduacaos", ignore = true)
    @Mapping(target = "removeGraduacao", ignore = true)
    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "ramalId", target = "ramal")
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
