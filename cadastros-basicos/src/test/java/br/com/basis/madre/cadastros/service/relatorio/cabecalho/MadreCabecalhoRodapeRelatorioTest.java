package br.com.basis.madre.cadastros.service.relatorio.cabecalho;

import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MadreCabecalhoRodapeRelatorioTest {
    @InjectMocks
    MadreCabecalhoRodapeRelatorio madreCabecalhoRodapeRelatorio;

    @Test
   public void buildTest(){
       VerticalListBuilder test = madreCabecalhoRodapeRelatorio.build();
    }

}
