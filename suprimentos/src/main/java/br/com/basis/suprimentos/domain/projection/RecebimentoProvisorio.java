package br.com.basis.suprimentos.domain.projection;

public interface RecebimentoProvisorio {
    Long getId();

    _DocumentoFiscalEntrada getNotaFiscalEntrada();

    _AutorizacaoFornecimento getAutorizacaoFornecimento();

    static interface _DocumentoFiscalEntrada {
        Long getId();

        Long getNumeroDocumento();
    }

    static interface _AutorizacaoFornecimento {
        Long getId();

        Long getNumero();
    }
}
