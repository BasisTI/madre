package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.OrteseProtese} entity.
 */
@Data
public class OrteseProteseDTO implements Serializable {

    private Long id;

    /**
     * descrição de procedimento especial ortese ou prótese
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimento especial ortese ou prótese", required = true)
    private String decricao;

}
