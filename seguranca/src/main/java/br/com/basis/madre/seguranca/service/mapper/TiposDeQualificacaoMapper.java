package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.TiposDeQualificacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TiposDeQualificacao} and its DTO {@link TiposDeQualificacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TiposDeQualificacaoMapper extends EntityMapper<TiposDeQualificacaoDTO, TiposDeQualificacao> {


    @Mapping(target = "graduacaoQualificacaos", ignore = true)
    @Mapping(target = "removeGraduacaoQualificacao", ignore = true)
    TiposDeQualificacao toEntity(TiposDeQualificacaoDTO tiposDeQualificacaoDTO);

    default TiposDeQualificacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        TiposDeQualificacao tiposDeQualificacao = new TiposDeQualificacao();
        tiposDeQualificacao.setId(id);
        return tiposDeQualificacao;
    }
}
