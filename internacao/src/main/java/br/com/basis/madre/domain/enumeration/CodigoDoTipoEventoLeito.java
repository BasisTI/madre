package br.com.basis.madre.domain.enumeration;

public enum CodigoDoTipoEventoLeito {
    LIBERACAO(1L), RESERVA(2L), BLOQUEIO(3L);

    private Long valor;

    CodigoDoTipoEventoLeito(Long valor) {
        this.valor = valor;
    }

    public Long getValor() {
        return this.valor;
    }
}
