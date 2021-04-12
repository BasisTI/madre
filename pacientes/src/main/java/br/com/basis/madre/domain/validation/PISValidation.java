package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.PIS;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PISValidation implements ConstraintValidator<PIS, String> {

    @Override
    public boolean isValid(String pis, ConstraintValidatorContext constraintValidatorContext) {
        if (pis == null){
            return true;
        } else {
            int total = 0;
            int[] peso = new int[]{3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            String parteNumerica = pis.replaceAll("\\.", "").replaceAll("\\-", "");
            StringBuilder sb = new StringBuilder();
            while (parteNumerica.length() < 11){
                sb.append("0");
                sb.append(parteNumerica);
                parteNumerica = sb.toString();
            }
            if(Long.parseLong(parteNumerica) == 0){
                return false;
            }
            for (int i = 1; i <= 10; i++) {
                total += Integer.parseInt(parteNumerica.substring(i - 1, i)) * peso[i - 1];
            }
            double resto = Math.floor(total % 11);
            if (resto != 0) {
                resto = 11 - resto;
            }
            return resto == Integer.parseInt(parteNumerica.substring(10, 11));
        }
    }
}
