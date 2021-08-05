package br.com.basis.madre.service.projection;

// import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public interface PessoaResumo {
    Long getId();
    String getNome();
    LocalDate getDataDeNascimento();
}
