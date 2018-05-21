package br.com.basis.madre.cadastros.service.relatorio.cabecalho;

import br.com.basis.dynamicexports.pojo.CabecalhoRodapeRelatorioInterface;
import br.com.basis.dynamicexports.service.impl.DynamicExportsServiceImpl;
import br.com.basis.dynamicexports.util.DynamicExportsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
public class MadreDynamicExportsServiceTest {
    @InjectMocks
    MadreDynamicExportsService madreDynamicExportsService;

    @Test
    public void obterCabacalhoRodabeRelatorioTest(){
        CabecalhoRodapeRelatorioInterface test = madreDynamicExportsService.obterCabecalhoRodapeRelatorio();
    }
}
