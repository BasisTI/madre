package br.com.basis.madre.cadastros.service.exception;

public class UsuarioException extends Exception {

    public static final long serialVersionUID = -1L;

    public static final String ERROR_PRE_CADASTRO_JA_EXISTENTE = "Já existe um registro com os dados informados.";

    public static final String CODE_REGISTRO_EXISTE_BASE = "preCadastro";

    public static String getCodeRegistroExisteBase() {
        return CODE_REGISTRO_EXISTE_BASE;
    }

    public UsuarioException(String message) {
        super(message);
    }
}


