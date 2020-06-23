package br.com.basis.madre.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Clinica} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaDTO implements Serializable {

    private Long id;

    private String nome;

}
