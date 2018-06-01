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
public class UnidadeHospitalarExceptionTest {

    @InjectMocks
    UnidadeHospitalarException unidadeHospitalarException;

    @Mock
    String message;

    @Test
    public void getCodeRegistroExisteBaseTest(){Assert.assertEquals("unidadeHospitalar",unidadeHospitalarException.getCodeRegistroExisteBase());}


    @Test
    public void unidadeHospitalarExceptionTest(){new UnidadeHospitalarException(message);}

}
