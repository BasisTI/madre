package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Paciente} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO implements Serializable {

    private Long id;

    private Long prontuario;

    @NotNull
    private String nome;

    private String nomeSocial;

    @NotNull
    private LocalDate dataDeNascimento;

    private Instant horaDeNascimento;

    private String email;

    private String observacao;

    @NotNull
    private GrauDeInstrucao grauDeInstrucao;

    @NotNull
    private Sexo sexo;

    private Long cartaoSUSId;

    private Long responsavelId;

    private Long documentoId;

    private Long certidaoId;

    private Long ocupacaoId;

    private Long religiaoId;

    private Long naturalidadeId;

    private Long etniaId;

    private Long genitoresId;

    private Long nacionalidadeId;

    private Long racaId;

    private Long estadoCivilId;


}
