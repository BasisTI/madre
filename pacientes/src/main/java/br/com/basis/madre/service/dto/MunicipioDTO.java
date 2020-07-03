package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Municipio} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String nomeDoDistrito;

    @NotNull
    private String ibge;


    private Long ufId;

}
