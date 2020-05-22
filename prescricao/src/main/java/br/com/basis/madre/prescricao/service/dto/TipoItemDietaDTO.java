package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.TipoItemDieta} entity.
 */

@Data
public class TipoItemDietaDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String descricao;

}
