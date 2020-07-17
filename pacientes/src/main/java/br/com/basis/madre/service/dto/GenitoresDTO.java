package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Genitores} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenitoresDTO implements Serializable {

    private Long id;

    private String prontuarioDaMae;

    @NotNull
    private String nomeDaMae;

    @NotNull
    private String nomeDoPai;


}
