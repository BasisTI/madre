package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioUnidadeHospitalarColunas extends  PropriedadesRelatorio {

        public RelatorioUnidadeHospitalarColunas() {
            super("Listagem Unidade Hospitalar", "Total Unidade Hospitalar");
            super.getColunas().add(new ColunasPropriedadeRelatorio("id", "id", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
        }
    }
