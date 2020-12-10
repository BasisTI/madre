package br.com.basis.madre.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public interface PacienteResumo {
    Long getId();
  
    Long getProntuario();

    String getNome();

    LocalDate getDataDeNascimento();

    Genitores_ getGenitores();

    CartaoSUS_ getCartaoSUS();

    static interface Genitores_ {
        String getNomeDaMae();
    }

    static interface CartaoSUS_ {
        String getNumero();
    }
}
