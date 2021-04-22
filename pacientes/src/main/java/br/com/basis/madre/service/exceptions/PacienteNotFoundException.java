package br.com.basis.madre.service.exceptions;

public class PacienteNotFoundException extends RuntimeException{

    public PacienteNotFoundException(String msg){
        super(msg);
    }
}
