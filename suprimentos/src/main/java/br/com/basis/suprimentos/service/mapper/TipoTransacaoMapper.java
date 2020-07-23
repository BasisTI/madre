package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.TipoTransacao;
import br.com.basis.suprimentos.service.dto.TipoTransacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TipoTransacaoMapper extends EntityMapper<TipoTransacaoDTO, TipoTransacao> {


    default TipoTransacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoTransacao tipoTransacao = new TipoTransacao();
        tipoTransacao.setId(id);
        return tipoTransacao;
    }
}
