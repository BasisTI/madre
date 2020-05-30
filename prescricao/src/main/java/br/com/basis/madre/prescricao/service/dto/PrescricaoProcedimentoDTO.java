package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoProcedimento} entity.
 */
@Data
public class PrescricaoProcedimentoDTO implements Serializable {

    private Long id;

    /**
     * Identificador do paciente
     */
    @NotNull
    @ApiModelProperty(value = "Identificador do paciente", required = true)
    private Long idPaciente;

    /**
     * Observação ou comentário para a prescrição de procedimento especial
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Observação ou comentário para a prescrição de procedimento especial")
    private String observacao;
    
    private Set<ItemPrescricaoProcedimentoDTO> itemPrescricaoProcedimentoDTO = new HashSet<>();


}
