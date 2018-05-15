package br.com.basis.madre.cadastros.web.rest.errors;

public class UploadException extends RuntimeException{
    public UploadException(String message, Throwable cause){
        super(message, cause);
    }
}
