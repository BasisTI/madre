package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsultaEstoqueAlmoxarifadoDTO implements Serializable {
    private AlmoxarifadoDTO almoxarifado;
    private FornecedorDTO fornecedor;
    private MaterialDTO material;
    private UnidadeMedidaDTO unidade;
    private GrupoMaterialDTO grupo;
    private ClassificacaoMaterialDTO classificacao;
    private Boolean estocavel;
    private Boolean ativo;
}
