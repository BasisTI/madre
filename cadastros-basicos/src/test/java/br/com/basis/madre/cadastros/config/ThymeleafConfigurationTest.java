package br.com.basis.madre.cadastros.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by pedro-ramalho on 04/06/18.
 */

@RunWith(PowerMockRunner.class)
public class ThymeleafConfigurationTest {



    @InjectMocks
    ThymeleafConfiguration thymeleafConfiguration;

    @InjectMocks
    ClassLoaderTemplateResolver emailTemplateResolver;


    @Test
    public void ThymeleafConfiguration(){new ThymeleafConfiguration();}




}
