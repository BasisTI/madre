package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioTipoPerguntaColunas extends PropriedadesRelatorio {

    public RelatorioTipoPerguntaColunas() {
        super("Listagem Tipo de Pergunta", "Total Tipo de Pergunta");
        super.getColunas().add(new ColunasPropriedadeRelatorio("enunciadoPergunta", "Enunciado", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));

    }
}
