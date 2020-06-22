package br.com.basis.suprimentos.domain.validator;

import br.com.basis.suprimentos.domain.annotation.CpfCnpj;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class CpfCnpjValidatorTest {

    Validator validator;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void cpfValidoTest() {
        final CpfPlaceholder placeholder = new CpfPlaceholder("04175516140");
        final Set<ConstraintViolation<CpfPlaceholder>> constraintViolations = validator.validate(placeholder);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void cpfInvalidoTest() {
        final CpfPlaceholder placeholder = new CpfPlaceholder("04175516141");
        final Set<ConstraintViolation<CpfPlaceholder>> constraintViolations = validator.validate(placeholder);
        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    void cnpjValidoTest() {
        final CnpjPlaceholder placeholder = new CnpjPlaceholder("71221524000150");
        final Set<ConstraintViolation<CnpjPlaceholder>> constraintViolations = validator.validate(placeholder);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void cnpjInvalidoTest() {
        final CnpjPlaceholder placeholder = new CnpjPlaceholder("71021524000150");
        final Set<ConstraintViolation<CnpjPlaceholder>> constraintViolations = validator.validate(placeholder);
        assertFalse(constraintViolations.isEmpty());
    }

    @RequiredArgsConstructor
    @Getter
    public static class CpfPlaceholder {
        @CpfCnpj
        private final String cpf;
    }

    @RequiredArgsConstructor
    @Getter
    public static class CnpjPlaceholder {
        @CpfCnpj
        private final String cnpj;
    }
}
