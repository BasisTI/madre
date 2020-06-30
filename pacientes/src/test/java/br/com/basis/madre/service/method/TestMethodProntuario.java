package br.com.basis.madre.service.method;

import br.com.basis.madre.service.ProntuarioService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TestMethodProntuario {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testMetodo() {
        ProntuarioService prontuarioService = new ProntuarioService(null);
        String a = "1201611227",str;
        int result = prontuarioService.calculoDV(a);
        int retornoEsperado = 3;
        System.out.println("Dígito verificador: "+result);
        Assert.assertEquals(result, retornoEsperado);

        if(result == retornoEsperado){
            str = a+""+""+result;
            System.out.println(str);
        }
    }

}
