package br.com.basis.madre.domain.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

public class SUSValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void numeroInvalido() {
        SUSValidatorTestBase sus = new SUSValidatorTestBase();
        sus.setNumeroSUS("123412345678911");
        Set<ConstraintViolation<SUSValidatorTestBase>> restricoes = validator.validate(sus);
        assertFalse(restricoes.isEmpty());
        assertEquals("Número do cartão SUS está inválido", restricoes.iterator().next().getMessageTemplate());

    }

    @Test
    public void numeroValido(){
        SUSValidatorTestBase sus = new SUSValidatorTestBase();
        sus.setNumeroSUS("777346334940007");
        Set<ConstraintViolation<SUSValidatorTestBase>> restricoes = validator.validate(sus);
        assertTrue( restricoes.isEmpty());
    }

    @Test
    public void susNull(){
        SUSValidatorTestBase sus = new SUSValidatorTestBase();
        sus.setNumeroSUS(null);
        Set<ConstraintViolation<SUSValidatorTestBase>> restricoes = validator.validate(sus);
        assertTrue(restricoes.isEmpty());
    }
}
