package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Recebimento;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Recebimento} and its DTO {@link RecebimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentoFiscalEntradaMapper.class, AutorizacaoFornecimentoMapper.class, ItemNotaRecebimentoMapper.class})
public interface RecebimentoMapper extends EntityMapper<RecebimentoDTO, Recebimento> {

    @Mapping(source = "notaFiscalEntrada.id", target = "notaFiscalEntradaId")
    @Mapping(source = "autorizacaoFornecimento.id", target = "autorizacaoFornecimentoId")
    RecebimentoDTO toDto(Recebimento recebimento);

    @Mapping(source = "itensNotaRecebimento", target = "itensNotaRecebimentos")
    @Mapping(source = "notaFiscalEntradaId", target = "notaFiscalEntrada")
    @Mapping(source = "autorizacaoFornecimentoId", target = "autorizacaoFornecimento")
    Recebimento toEntity(RecebimentoDTO recebimentoDTO);

    default Recebimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recebimento recebimento = new Recebimento();
        recebimento.setId(id);
        return recebimento;
    }
}
