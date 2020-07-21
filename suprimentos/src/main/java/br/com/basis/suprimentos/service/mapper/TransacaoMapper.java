package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Transacao;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TipoTransacaoMapper.class, LancamentoMapper.class})
public interface TransacaoMapper extends EntityMapper<TransacaoDTO, Transacao> {

    @Mapping(source = "tipoTransacao.id", target = "tipoTransacaoId")
    @Mapping(source = "lancamento.id", target = "lancamentoId")
    TransacaoDTO toDto(Transacao transacao);

    @Mapping(source = "tipoTransacaoId", target = "tipoTransacao")
    @Mapping(source = "lancamentoId", target = "lancamento")
    Transacao toEntity(TransacaoDTO transacaoDTO);

    default Transacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transacao transacao = new Transacao();
        transacao.setId(id);
        return transacao;
    }
}
