package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.PISAnnotation;

public class PISValidatorTestBase {

    @PISAnnotation
    private String pis;

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }
}
