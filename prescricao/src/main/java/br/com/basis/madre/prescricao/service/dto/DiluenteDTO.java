package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.Diluente} entity.
 */
@Data
public class DiluenteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String descricao;
    
}
