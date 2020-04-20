package br.com.basis.madre.domain.validation.annotation;

import br.com.basis.madre.domain.validation.CartaoSUSValidation;
import br.com.basis.madre.domain.validation.PISValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PISValidation.class)
public @interface PIS {

    String message () default "PIS/PASEP inválido";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

    String value() default "";
}
