package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.EspeciaisDiversos} entity.
 */
@Data
public class EspeciaisDiversosDTO implements Serializable {

    private Long id;

    /**
     * descrição de procedimentos especiais diversos
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimentos especiais diversos", required = true)
    private String descricao;

}
