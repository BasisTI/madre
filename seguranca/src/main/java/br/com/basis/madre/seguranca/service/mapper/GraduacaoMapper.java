package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.Graduacao;
import br.com.basis.madre.seguranca.service.dto.GraduacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link Graduacao} and its DTO {@link GraduacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServidorMapper.class, TiposDeQualificacaoMapper.class, InstituicaoMapper.class})
public interface GraduacaoMapper extends EntityMapper<GraduacaoDTO, Graduacao> {

    @Mapping(source = "servidor.id", target = "servidorId")
    @Mapping(source = "tiposDeQualificacao.id", target = "tiposDeQualificacaoId")
    @Mapping(source = "instituicao.id", target = "instituicaoId")
    GraduacaoDTO toDto(Graduacao graduacao);

    @Mapping(source = "servidorId", target = "servidor")
    @Mapping(source = "tiposDeQualificacaoId", target = "tiposDeQualificacao")
    @Mapping(source = "instituicaoId", target = "instituicao")
    Graduacao toEntity(GraduacaoDTO graduacaoDTO);

    default Graduacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Graduacao graduacao = new Graduacao();
        graduacao.setId(id);
        return graduacao;
    }
}
