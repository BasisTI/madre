package br.com.basis.madre.domain.validation.annotation;

import br.com.basis.madre.domain.validation.CartaoSUSValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CartaoSUSValidation.class)
public @interface CartaoSUSAnnotation {

    String message () default "Número do cartão SUS está inválido";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

    String value() default "";


}
