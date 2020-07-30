package br.com.basis.suprimentos.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoEstoqueAlmoxarifado implements Serializable {
    private Long id;
    private Boolean ativo;
    private Boolean estocavel;
    private Long quantidadeDisponivel;
    private Long quantidadeBloqueada;
    private Long quantidadeProblema;
    private String nomeMaterial;
    private String nomeFantasia;
    private String nomeAlmoxarifado;
}
