package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioTipoRespotaColunas extends PropriedadesRelatorio {

    public RelatorioTipoRespotaColunas(){
        super("Listagem Tipo de Resposta", "Total Tipo de Resposta");
        super.getColunas().add(new ColunasPropriedadeRelatorio("resposta","Enuciado", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
    }
}
