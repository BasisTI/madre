package br.com.basis.madre.cadastros.service.relatorio.cabecalho;

import br.com.basis.dynamicexports.pojo.CabecalhoRodapeRelatorioInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MadreDynamicExportsServiceTest {
    @InjectMocks
    MadreDynamicExportsService madreDynamicExportsService;

    @Test
    public void obterCabacalhoRodabeRelatorioTest(){
        CabecalhoRodapeRelatorioInterface test = madreDynamicExportsService.obterCabecalhoRodapeRelatorio();
    }
}
