package br.com.basis.madre.cadastros.service.relatorio.colunas;

import br.com.basis.dynamicexports.constants.DynamicExportsConstants;
import br.com.basis.dynamicexports.pojo.ColunasPropriedadeRelatorio;
import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;

public class RelatorioPreCadastroColunas extends  PropriedadesRelatorio {

        public RelatorioPreCadastroColunas() {
            super("Listagem Pré Cadastro", "Total Pré Cadastro");
            super.getColunas().add(new ColunasPropriedadeRelatorio("nomeDoPaciente", "Nome do Paciente", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("nomeSocial", "Nome Social", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("nomeDaMae", "Nome da Mãe", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("dataNascimentoString", "Data de Nascimento", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));
            super.getColunas().add(new ColunasPropriedadeRelatorio("numCartaoSus", "Cartão SUS", String.class, 10, DynamicExportsConstants.MASCARA_NULL, DynamicExportsConstants.ALINHAR_ESQUERDA));


        }
    }
