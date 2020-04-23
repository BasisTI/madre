package br.com.basis.madre.domain.validation;


import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class PISValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void numeroInvalido() {
        PISValidatorTestBase pis = new PISValidatorTestBase();
        pis.setPis("12345678911");
        Set<ConstraintViolation<PISValidatorTestBase>> restricoes = validator.validate(pis);
        assertFalse(restricoes.isEmpty());
        assertEquals("PIS/PASEP inválido", restricoes.iterator().next().getMessageTemplate());


    }

    @Test
    public void numeroValido(){
        PISValidatorTestBase pis = new PISValidatorTestBase();
        pis.setPis("21392062715");
        Set<ConstraintViolation<PISValidatorTestBase>> restricoes = validator.validate(pis);
        assertTrue( true);
    }

}
