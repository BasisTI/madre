package br.com.basis.madre.domain.validation;


import br.com.basis.madre.domain.validation.annotation.PIS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PISValidation implements ConstraintValidator<PIS, String> {

    @Override
    public void initialize(PIS constraintAnnotation) {
        // Valor não está sendo utilizado
    }

    @Override
    public boolean isValid(String pis, ConstraintValidatorContext constraintValidatorContext) {
        if (pis == null){
            return true;
        } else {
            int total = 0;
            int[] peso = new int[]{3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            String parteNumerica = pis.replaceAll("\\.", "").replaceAll("\\-", "");
            while (parteNumerica.length() < 11) parteNumerica = "0" + parteNumerica;
            if (Long.valueOf(parteNumerica).longValue() == 0){
                return false;
            }
            for (int i = 1; i <= 10; i++) {
                total += Integer.valueOf(parteNumerica.substring(i - 1, i)).intValue() * peso[i - 1];
            }
            int resto = Integer.valueOf(total % 11).intValue();
            if (resto != 0) {
                resto = 11 - resto;
            }
            return resto == Integer.valueOf(parteNumerica.substring(10, 11)).intValue();
        }
    }
}
