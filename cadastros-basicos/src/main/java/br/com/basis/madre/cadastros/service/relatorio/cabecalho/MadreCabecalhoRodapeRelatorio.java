package br.com.basis.madre.cadastros.service.relatorio.cabecalho;

import br.com.basis.dynamicexports.pojo.CabecalhoRodapeRelatorioInterface;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

public class MadreCabecalhoRodapeRelatorio implements CabecalhoRodapeRelatorioInterface {

    private String nomeSistema;

    public MadreCabecalhoRodapeRelatorio(String nomeSistema) {
        super();
        this.nomeSistema = nomeSistema;
    }

    public VerticalListBuilder build() {
        VerticalListBuilder cabecalho = cmp.verticalList();
        cabecalho.add(cmp.text(nomeSistema)).removeLineWhenBlank();
        return cabecalho;
    }

}
