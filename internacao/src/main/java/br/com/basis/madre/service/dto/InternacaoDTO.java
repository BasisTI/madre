package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.Prioridade;
import br.com.basis.madre.domain.enumeration.TipoDeAlta;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class InternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long pacienteId;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private String justificativa;

    @NotNull
    private ZonedDateTime dataDaInternacao;

    private Boolean diferencaDeClasse;

    private Boolean solicitarProntuario;

    private Long leitoId;

    private Long especialidadeId;

    private Long crmId;

    private Long hospitalDeOrigemId;

    private Long origemId;

    private Long convenioId;

    private Long planoId;

    private Long procedimentoId;

    private Long procedenciaId;

    private Long modalidadeAssistencialId;

    private Long localDeAtendimentoId;

    private Long caraterDaInternacaoId;

    private Boolean ativo;

    private LocalDate dataDaAlta;

    private LocalDate previsaoDeAlta;

    private TipoDeAlta tipoDeAlta;

}
