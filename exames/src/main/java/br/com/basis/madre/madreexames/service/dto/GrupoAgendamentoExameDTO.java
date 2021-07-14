package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame} entity.
 */
public class GrupoAgendamentoExameDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Integer codigo;

    private Boolean agendarEmConjunto;

    private Boolean calcularOcupacao;

    @NotNull
    private Boolean ativo;

    private Set<ExameDTO> grupoAgendamentos = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean isAgendarEmConjunto() {
        return agendarEmConjunto;
    }

    public void setAgendarEmConjunto(Boolean agendarEmConjunto) {
        this.agendarEmConjunto = agendarEmConjunto;
    }

    public Boolean isCalcularOcupacao() {
        return calcularOcupacao;
    }

    public void setCalcularOcupacao(Boolean calcularOcupacao) {
        this.calcularOcupacao = calcularOcupacao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<ExameDTO> getGrupoAgendamentos() {
        return grupoAgendamentos;
    }

    public void setGrupoAgendamentos(Set<ExameDTO> exames) {
        this.grupoAgendamentos = exames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAgendamentoExameDTO)) {
            return false;
        }

        return id != null && id.equals(((GrupoAgendamentoExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAgendamentoExameDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo=" + getCodigo() +
            ", agendarEmConjunto='" + isAgendarEmConjunto() + "'" +
            ", calcularOcupacao='" + isCalcularOcupacao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", grupoAgendamentos='" + getGrupoAgendamentos() + "'" +
            "}";
    }
}
