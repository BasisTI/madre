package br.com.basis.madre.domain.validation;

import br.com.basis.madre.domain.Documento;
import br.com.basis.madre.domain.validation.annotation.PIS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

public class PISNullTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void verifica(){
        PISValidatorTestBase pis = new PISValidatorTestBase();
        pis.setPis(null);
        Set<ConstraintViolation<PISValidatorTestBase>> restricoes = validator.validate(pis);
        assertTrue(restricoes.isEmpty());
    }


}
