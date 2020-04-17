package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.CartaoSUSAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CartaoSUSValidation implements ConstraintValidator<CartaoSUSAnnotation, String> {

    private String value;

    @Override
    public void initialize(CartaoSUSAnnotation constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    private int somaPonderada(String s) {
        char[] cs = s.toCharArray();
        int soma = 0;
        for (int i = 0; i < cs.length; i++) {
            soma += Character.digit(cs[i], 10) * (15 - i);
        }
        return soma;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches("[1-2]\\d{10}00[0-1]\\d") || s.matches("[7-9]\\d{14}")) {
            return somaPonderada(s) % 11 == 0;
        }
        return false;
    }


}
