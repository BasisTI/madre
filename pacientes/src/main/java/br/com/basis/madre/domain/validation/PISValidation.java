package br.com.basis.madre.domain.validation;


import br.com.basis.madre.domain.validation.annotation.PIS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PISValidation implements ConstraintValidator<PIS, String> {

    @Override
    public void initialize(PIS constraintAnnotation) {

    //Value não está sendo utilizado    this.value = constraintAnnotation.value();
    }


    @Override
    public boolean isValid(String pis, ConstraintValidatorContext constraintValidatorContext) {

        int total = 0;
        int[] peso = new int[]{3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        String parteNumerica = pis.replaceAll("\\.", "").replaceAll("\\-", "");

        while (parteNumerica.length() < 11) parteNumerica = "0" + parteNumerica;

        if (Long.valueOf(parteNumerica) == 0) return false;

        for (int i = 1; i <= 10; i++) {
            total += Integer.valueOf(parteNumerica.substring(i - 1, i)).intValue() * peso[i - 1];
        }

        int resto = Integer.valueOf(total % 11);

        if (resto != 0) {
            resto = 11 - resto;
        }

        if (resto != Integer.valueOf(parteNumerica.substring(10, 11))) {
            return false;
        } else {
            return true;
        }
    }
}
