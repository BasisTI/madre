package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RecebimentoDTO implements Serializable {
    private Long id;

    private Long notaFiscalEntradaId;

    private Long autorizacaoFornecimentoId;

    private Set<ItemNotaRecebimentoDTO> itensNotaRecebimento;
}
