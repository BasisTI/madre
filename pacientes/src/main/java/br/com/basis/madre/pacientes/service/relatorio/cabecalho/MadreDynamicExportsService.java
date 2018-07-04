package br.com.basis.madre.pacientes.service.relatorio.cabecalho;

import br.com.basis.dynamicexports.pojo.CabecalhoRodapeRelatorioInterface;
import br.com.basis.dynamicexports.service.impl.DynamicExportsServiceImpl;
import br.com.basis.dynamicexports.util.DynamicExportsBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MadreDynamicExportsService extends DynamicExportsServiceImpl {

    private static final String REPORT_NOME_SISTEMA = "Madre - Sistema de Gestão Hospitalar Basis.";


    public MadreDynamicExportsService(DynamicExportsBuilder dynamicExportsBuilder) {
        super(dynamicExportsBuilder);
    }

    @Override
    public CabecalhoRodapeRelatorioInterface obterCabecalhoRodapeRelatorio() {
        return new MadreCabecalhoRodapeRelatorio(MadreDynamicExportsService.REPORT_NOME_SISTEMA);
    }
}
