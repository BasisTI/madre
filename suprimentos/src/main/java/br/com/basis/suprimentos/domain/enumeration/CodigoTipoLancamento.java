package br.com.basis.suprimentos.domain.enumeration;

public enum CodigoTipoLancamento {
    INCLUSAO_SALDO(1L, "Inclusão de Saldo");

    private Long codigo;
    private String descricao;

    CodigoTipoLancamento(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CodigoTipoLancamento toEnum(Long codigo) {
        if (codigo == null) {
            return null;
        }

        for (CodigoTipoLancamento tipoLancamento : CodigoTipoLancamento.values()) {
            if (codigo.equals(tipoLancamento.getCodigo())) {
                return tipoLancamento;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
