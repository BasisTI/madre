package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.validation.annotation.CartaoSUS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CartaoSUSValidation implements ConstraintValidator<CartaoSUS, String> {

    @Override
    public void initialize(CartaoSUS constraintAnnotation) {

        //Value não está sendo ultilizado  this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        final int quantity = 15;

        if (s==null){
            return true;
        } else{
            if (s.length() == quantity) {
                return somaPonderada(s) % 11 == 0;
            }
            return false;
        }


    }
    private int somaPonderada(String s) {
        char[] cs = s.toCharArray();
        int soma = 0;
        for (int i = 0; i < cs.length; i++) {
            soma += Character.digit(cs[i], 10) * (15 - i);
        }
        return soma;
    }





}
