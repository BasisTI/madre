package br.com.basis.madre.cadastros.service.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by pedro-ramalho on 01/06/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class RelatórioExceptionTest {

    @InjectMocks
    RelatorioException relatorioException;

    @Test
    public void relatorioExceptionTest(){new RelatorioException();}

    @Test
    public void getMessageTest(){Assert.assertEquals("Erro ao imprimir o relatório.",relatorioException.getMessage());}

    @Test
    public void getCodeEntidadeTest(){Assert.assertEquals("ENTIDADE",relatorioException.getCodeEntidade());}


}
