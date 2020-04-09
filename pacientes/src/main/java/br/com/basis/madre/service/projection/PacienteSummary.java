package br.com.basis.madre.service.projection;

import java.time.LocalDate;

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
