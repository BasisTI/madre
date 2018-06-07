package br.com.basis.madre.cadastros.service.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroExceptionTest {
    @InjectMocks
    PreCadastroException preCadastroException;

    @Test
    public void getCodeRegistroExisteBaseTest(){
        Assert.assertEquals("preCadastro", preCadastroException.getCodeRegistroExisteBase());
    }

    @Test
    public void testMessage(){
        new PreCadastroException("abcd");
    }
}
