package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.Prioridade;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoDeInternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataProvavelDaInternacao;

    private LocalDate dataProvavelDaCirurgia;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private String principaisSinaisESintomasClinicos;

    @NotNull
    private String condicoesQueJustificamInternacao;

    @NotNull
    private String principaisResultadosProvasDiagnosticas;


    private Long cidPrincipalId;

    private Long cidSecundarioId;

    private Long equipeId;

    private Long crmId;

    private Long procedimentoId;

}
