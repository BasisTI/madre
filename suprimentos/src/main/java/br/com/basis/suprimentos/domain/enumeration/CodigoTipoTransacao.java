package br.com.basis.suprimentos.domain.enumeration;

public enum CodigoTipoTransacao {
    CREDITO(1L, "Cŕedito");

    private Long codigo;
    private String descricao;

    CodigoTipoTransacao(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CodigoTipoTransacao toEnum(Long codigo) {
        if (codigo == null) {
            return null;
        }

        for (CodigoTipoTransacao tipoTransacao : CodigoTipoTransacao.values()) {
            if (codigo.equals(tipoTransacao.getCodigo())) {
                return tipoTransacao;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
