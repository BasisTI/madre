package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.CartaoSUS;

public class SUSValidatorTestBase {
    @CartaoSUS
    private String numeroSUS;

    public String getNumeroSUS() {
        return numeroSUS;
    }

    public void setNumeroSUS(String numeroSUS) {
        this.numeroSUS = numeroSUS;
    }
}
