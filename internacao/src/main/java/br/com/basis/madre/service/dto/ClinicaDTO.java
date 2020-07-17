package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

import java.util.Objects;


/**
 * A DTO for the {@link br.com.basis.madre.domain.Clinica} entity.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaDTO implements Serializable {

    private Long id;

    private String descricao;

    private Integer capacidadeReferencial;

    private String numeroSUS;

    private Integer idadeMinimaInternacao;

    private Integer idadeMaximaInternacao;

    private Integer idadeMinimaAmbulatorio;

    private Integer idadeMaximaAmbulatorio;

}
