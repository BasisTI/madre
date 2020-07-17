package br.com.basis.madre.service.dto;


import br.com.basis.madre.domain.enumeration.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import java.util.Objects;


/**
 * A DTO for the {@link br.com.basis.madre.domain.Unidade} entity.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String sigla;

    @NotNull
    private Situacao situacao;

    private Boolean controleDeEstoque;

    private Long idAlmoxarifado;

    @NotNull
    private Integer andar;

    private Integer capacidade;

    private Instant horarioInicio;

    private Instant horarioFim;

    private String localExame;

    private String rotinaDeFuncionamento;

    private Boolean anexoDocumento;

    private Long setor;

    @NotNull
    private Long idCentroDeAtividade;

    private Long idChefia;


    private Long unidadePaiId;

    private Long alaId;

    private Long clinicaId;

    private Long tipoUnidadeId;

    private Long prescricaoEnfermagemId;

    private Long prescricaoMedicaId;

    private Long cirurgiaId;

    private Set<CaracteristicaDTO> caracteristicas = new HashSet<>();


}
