package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioEspecialidadeColunas extends  PropriedadesRelatorio {

        public RelatorioEspecialidadeColunas() {
            super("Listagem Especialidade", "Total Especialidade");
            super.getColunas().add(new ColunasPropriedadeRelatorio("nome", "Nome da Especialidade", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("descricao", "Descrição da Especialidade", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
        }
    }