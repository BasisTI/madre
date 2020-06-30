package br.com.basis.suprimentos.domain.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ConstraintComposition(CompositionType.OR)
@CNPJ
@CPF
@ReportAsSingleViolation
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface CpfCnpj {
    String message() default "CPF/CNPJ inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
