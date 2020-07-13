package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico} entity.
 */
@Data
public class ItemPrescricaoDiagnosticoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idCid;

    /**
     * Identificador do paciente
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Identificador do paciente")
    private String complemento;


    private Long prescricaoDiagnosticoId;

}
