package br.com.basis.madre.domain.enumeration;

public enum CodigoDeSituacaoDeLeito {
    DESOCUPADO(1L), BLOQUEADO(2L), RESERVADO(3L), OCUPADO(4L);

    private Long valor;

    CodigoDeSituacaoDeLeito(Long valor) {
        this.valor = valor;
    }

    public Long getValor() {
        return this.valor;
    }
}

