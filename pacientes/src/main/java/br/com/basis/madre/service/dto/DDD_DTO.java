package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link br.com.basis.madre.domain.DDD} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DDD_DTO {

    private Long id;

    @NotNull
    private String valor;
}
