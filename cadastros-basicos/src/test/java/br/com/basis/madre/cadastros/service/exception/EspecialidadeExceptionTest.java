package br.com.basis.madre.cadastros.service.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by pedro-ramalho on 01/06/18.
 */
@RunWith(PowerMockRunner.class)
public class EspecialidadeExceptionTest {

    @InjectMocks
    EspecialidadeException especialidadeException;

    @Mock
    String message;


    @Test
    public void especialidadeExceptionTest(){ new EspecialidadeException(message);}

    @Test
    public void getCodeRegistroExisteBaseTest(){Assert.assertEquals("especialidade",especialidadeException.getCodeRegistroExisteBase());}


}
