package br.com.basis.madre.domain.enumeration;

public enum CodigoDoTipoEventoLeito {
    LIBERACAO(1L), RESERVA(2L), BLOQUEIO(3L), OCUPACAO(4L);

    private Long valor;

    CodigoDoTipoEventoLeito(Long valor) {
        this.valor = valor;
    }

    public Long getValor() {
        return this.valor;
    }

    public static class Constants {
        public static final String LIBERACAO_ID = "1";
        public static final String RESERVA_ID = "2";
        public static final String BLOQUEIO_ID = "3";
        public static final String OCUPACAO_ID = "4";
    }
}
