package br.com.basis.madre.cadastros;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by pedro-ramalho on 04/06/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationWebXmlTest {

    @InjectMocks
    ApplicationWebXml applicationWebXml;

    @InjectMocks
    SpringApplicationBuilder app;

    @Mock
    CadastrosbasicosApp cadastrosbasicosApp;

    @Mock
    Runner runner;

    @Test
    public void applicationWebXmlTest(){new ApplicationWebXml();}

    @Test
    public void configureTest(){Assert.assertEquals(app.sources(CadastrosbasicosApp.class),applicationWebXml.configure(app));}

}
