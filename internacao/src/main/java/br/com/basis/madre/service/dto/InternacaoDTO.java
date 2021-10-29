package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.Prioridade;
import br.com.basis.madre.domain.enumeration.TipoDeAlta;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

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

    private String leitoNome;

    private Long especialidadeId;

    private String especialidadeNome;

    private Long crmId;

    private Long hospitalDeOrigemId;

    private Long origemId;

    private Long convenioId;

    private String convenioNome;

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

   /*public InternacaoDTO (Long id, ZonedDateTime dataDaInternacao, LocalDate dataDaAlta,
        String leitoNome, String especialidadeNome, String convenioNome) {

        this.id = id;
        this.dataDaInternacao = dataDaInternacao;
        this.dataDaAlta = dataDaAlta;
        this.leitoNome = leitoNome;
        this.especialidadeNome = especialidadeNome;
        this.convenioNome = convenioNome;
    }*/

    public String getLeitoNome() {
        return leitoNome;
    }

    public void setLeitoNome(String leitoNome) {
        this.leitoNome = leitoNome;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public void setConvenioNome(String convenioNome) {
        this.convenioNome = convenioNome;
    }

    public String getEspecialidadeNome() {
        return especialidadeNome;
    }

    public void setEspecialidadeNome(String especialidadeNome) {
        this.especialidadeNome = especialidadeNome;
    }



    @Override
    public String toString() {
        return "InternacaoDTO{" +
            "id=" + id +
            ", dataDaInternacao=" + dataDaInternacao +
            ", leitoNome='" + leitoNome + '\'' +
            ", especialidadeNome='" + especialidadeNome + '\'' +
            ", convenioNome='" + convenioNome + '\'' +
            ", dataDaAlta=" + dataDaAlta +
            '}';
    }
}
