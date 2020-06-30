package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class MaterialDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 120)
    private String nome;

    @Size(max = 2000)
    private String descricao;

    @NotNull
    private Boolean ativo;

    @Size(max = 120)
    private String regimento;

    @Size(max = 500)
    private String observacao;

    @Min(value = 0L)
    private Long unidadeId;

    @Min(value = 0L)
    private Long procedimentoId;

    private Long unidadeMedidaId;

    private Long grupoId;

    private Long localEstoqueId;

    private Long codigoCatmatId;

    private Long sazonalidadeId;

    private Long tipoResiduoId;

    private Long origemParecerTecnicoId;
}
