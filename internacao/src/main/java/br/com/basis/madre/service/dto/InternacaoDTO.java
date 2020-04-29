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
public class InternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private String justificativa;

    @NotNull
    private LocalDate dataDaInternacao;

    private Boolean diferencaDeClasse;

    private Boolean solicitarProntuario;


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

}
