package br.com.basis.madre.pacientes.service.exception;

public class PacienteException extends Exception  {


    public static final long serialVersionUID = -1L;

    public static final String ERROR_PACIENTE_JA_EXISTENTE = "Já existe um registro com os dados informados.";

    public static final String CODE_REGISTRO_EXISTE_BASE = "paciente";

    public static String getCodeRegistroExisteBase() {
        return CODE_REGISTRO_EXISTE_BASE;
    }

    public PacienteException(String message) { super(message); }

}
