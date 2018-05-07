package br.com.basis.madre.cadastros.service.exception;

public class RelatorioException extends Exception {

    private static final String ERRO_IMPRIMIR_RELATORIO = "Erro ao imprimir o relatório.";

    private static final String CODE_ENTIDADE = "ENTIDADE";

    @Override
    public String getMessage() {
        return ERRO_IMPRIMIR_RELATORIO;
    }

    public RelatorioException(Throwable var1) {
        super(var1);
    }

    public RelatorioException() {
    }

    public static String getCodeEntidade() {
        return CODE_ENTIDADE;
    }
}


