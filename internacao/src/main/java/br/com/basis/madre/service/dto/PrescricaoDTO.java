package br.com.basis.madre.service.dto;


import br.com.basis.madre.domain.enumeration.UnidadeTempo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

import java.util.Objects;


/**
 * A DTO for the {@link br.com.basis.madre.domain.Prescricao} entity.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescricaoDTO implements Serializable {

    private Long id;

    private Instant horarioValidade;

    private Integer tempoAdiantamento;

    private UnidadeTempo unidadeTempo;

    private Integer numeroVias;


}
