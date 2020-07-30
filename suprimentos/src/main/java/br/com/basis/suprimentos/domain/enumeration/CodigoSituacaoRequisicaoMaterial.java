package br.com.basis.suprimentos.domain.enumeration;

public enum CodigoSituacaoRequisicaoMaterial {
    GERADA(1L, "Gerada"), CONFIRMADA(2L, "Confirmada");

    private Long codigo;
    private String descricao;

    private CodigoSituacaoRequisicaoMaterial(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CodigoSituacaoRequisicaoMaterial toEnum(Long codigo) {
        if (codigo == null) {
            return null;
        }

        for (CodigoSituacaoRequisicaoMaterial situacao : CodigoSituacaoRequisicaoMaterial.values()) {
            if (codigo.equals(situacao.getCodigo())) {
                return situacao;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
