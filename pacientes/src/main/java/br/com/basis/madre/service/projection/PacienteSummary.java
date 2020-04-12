package br.com.basis.madre.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public interface PacienteSummary {
    String getNome();
    LocalDate getDataDeNascimento();
    GenitoresSummary getGenitores();
    CartaoSUSSummary getCartaoSUS();

    static interface GenitoresSummary {
        String getNomeDaMae();
    }

    static interface CartaoSUSSummary {
        String getNumero();
    }
}
