package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

import java.util.Objects;


/**
 * A DTO for the {@link br.com.basis.madre.domain.Cirurgia} entity.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaDTO implements Serializable {

    private Long id;

    private Instant tempoMax;

    private Instant tempoMin;

    private Integer limiteDias;

    private Integer limteDiasConvenios;

    private Integer intervalocirurgia;

    private Integer intervaloProcedimento;

}
