package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioUsuarioColunas extends  PropriedadesRelatorio {

        public RelatorioUsuarioColunas() {
            super("Listagem de Usuários", "Total de Usuários");
            super.getColunas().add(new ColunasPropriedadeRelatorio("nome", "Nome", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
        }
    }
