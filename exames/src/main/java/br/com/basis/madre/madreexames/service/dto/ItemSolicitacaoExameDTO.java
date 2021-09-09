package br.com.basis.madre.madreexames.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import br.com.basis.madre.madreexames.domain.enumeration.Situacao;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.ItemSolicitacaoExame} entity.
 */
public class ItemSolicitacaoExameDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean urgente;

    @NotNull
    private LocalDate dataProgramada;

    @NotNull
    private Situacao situacao;


    private Long itemSolicitacaoExameId;

    private String itemSolicitacaoExameSituacao;

    private Long solicitacaoExameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    public LocalDate getDataProgramada() {
        return dataProgramada;
    }

    public void setDataProgramada(LocalDate dataProgramada) {
        this.dataProgramada = dataProgramada;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Long getItemSolicitacaoExameId() {
        return itemSolicitacaoExameId;
    }

    public void setItemSolicitacaoExameId(Long exameId) {
        this.itemSolicitacaoExameId = exameId;
    }

    public String getItemSolicitacaoExameSituacao() {
        return itemSolicitacaoExameSituacao;
    }

    public void setItemSolicitacaoExameSituacao(String exameSituacao) {
        this.itemSolicitacaoExameSituacao = exameSituacao;
    }

    public Long getSolicitacaoExameId() {
        return solicitacaoExameId;
    }

    public void setSolicitacaoExameId(Long solicitacaoExameId) {
        this.solicitacaoExameId = solicitacaoExameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemSolicitacaoExameDTO)) {
            return false;
        }

        return id != null && id.equals(((ItemSolicitacaoExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemSolicitacaoExameDTO{" +
            "id=" + getId() +
            ", urgente='" + isUrgente() + "'" +
            ", dataProgramada='" + getDataProgramada() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", itemSolicitacaoExameId=" + getItemSolicitacaoExameId() +
            ", itemSolicitacaoExameSituacao='" + getItemSolicitacaoExameSituacao() + "'" +
            ", solicitacaoExameId=" + getSolicitacaoExameId() +
            "}";
    }
}
