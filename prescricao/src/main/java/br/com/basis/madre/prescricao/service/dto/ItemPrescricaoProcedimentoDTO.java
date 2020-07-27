package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento} entity.
 */
@Data
public class ItemPrescricaoProcedimentoDTO implements Serializable {

    private Long id;

    /**
     * Tipo do procedimento especial
     */
    @ApiModelProperty(value = "Tipo do procedimento especial")
    private TipoProcedimentoEspecial tipoProcedimentoEspecial;

    /**
     * Quanditade da Ortese ou prótese, valor deve ser um inteiro
     */
    @Min(value = 0)
    @ApiModelProperty(value = "Quanditade da Ortese ou prótese, valor deve ser um inteiro")
    private Integer quantidadeOrteseProtese;

    /**
     * Informações complementares para o procedimento
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Informações complementares para o procedimento")
    private String informacoes;

    /**
     * Justificativa para o procedimento especial
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Justificativa para o procedimento especial")
    private String justificativa;

    /**
     * Duração do procedimento solicitado
     */
    @Min(value = 0)
    @ApiModelProperty(value = "Duração do procedimento solicitado")
    private Integer duracaoSolicitada;

    /**
     * Chave estrangeira de tipo procedimento
     */
    @ApiModelProperty(value = "Chave estrangeira de tipo procedimento")

    private TipoProcedimentoDTO tipoProcedimentoId;

    private Long prescricaoProcedimentoId;

}
