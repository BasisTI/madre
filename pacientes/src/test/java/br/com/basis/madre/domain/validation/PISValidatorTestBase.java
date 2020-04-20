package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.PIS;

public class PISValidatorTestBase {

    @PIS
    private String pis;

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }
}
