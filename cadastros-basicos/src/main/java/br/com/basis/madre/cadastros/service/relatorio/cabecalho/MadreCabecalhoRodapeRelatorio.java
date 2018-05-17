package br.com.basis.madre.cadastros.service.relatorio.cabecalho;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

import br.com.basis.dynamicexports.pojo.CabecalhoRodapeRelatorioInterface;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

public class MadreCabecalhoRodapeRelatorio implements CabecalhoRodapeRelatorioInterface {

    public MadreCabecalhoRodapeRelatorio(String nomeSistema) {
        super();
        this.nomeSistema = nomeSistema;
    }

    private String nomeSistema;

    public VerticalListBuilder build() {
        VerticalListBuilder cabecalho = cmp.verticalList();
        cabecalho.add(cmp.text(nomeSistema)).removeLineWhenBlank();
        return cabecalho;
    }

}
