package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.SituacaoRequisicao;
import br.com.basis.suprimentos.service.dto.SituacaoRequisicaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SituacaoRequisicaoMapper extends EntityMapper<SituacaoRequisicaoDTO, SituacaoRequisicao> {
    default SituacaoRequisicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        SituacaoRequisicao situacaoRequisicao = new SituacaoRequisicao();
        situacaoRequisicao.setId(id);
        return situacaoRequisicao;
    }
}
