package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioUnidadeHospitalarColunas extends  PropriedadesRelatorio {


        public RelatorioUnidadeHospitalarColunas() {
            super("Listagem Unidade Hospitalar", "Total Unidade Hospitalar");
            super.getColunas().add(new ColunasPropriedadeRelatorio("nome", "Nome", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("sigla", "Sigla", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("cnpj", "Cnpj", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("endereco", "Endereço", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
        }
    }
