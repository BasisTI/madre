package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.madre.madreexames.domain.enumeration.Situacao;

/**
 * A ItemSolicitacaoExame.
 */
@Entity
@Table(name = "item_solicitacao_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemsolicitacaoexame")
public class ItemSolicitacaoExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemSolicitacaoExame")
    @SequenceGenerator(name = "seqItemSolicitacaoExame")
    private Long id;

    @NotNull
    @Column(name = "urgente", nullable = false)
    private Boolean urgente;

    @NotNull
    @Column(name = "data_programada", nullable = false)
    private LocalDate dataProgramada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemSolicitacaoExames", allowSetters = true)
    private Exame itemSolicitacaoExame;

    @ManyToOne
    @JsonIgnoreProperties(value = "solicitacaoExames", allowSetters = true)
    private SolicitacaoExame solicitacaoExame;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isUrgente() {
        return urgente;
    }

    public ItemSolicitacaoExame urgente(Boolean urgente) {
        this.urgente = urgente;
        return this;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    public LocalDate getDataProgramada() {
        return dataProgramada;
    }

    public ItemSolicitacaoExame dataProgramada(LocalDate dataProgramada) {
        this.dataProgramada = dataProgramada;
        return this;
    }

    public void setDataProgramada(LocalDate dataProgramada) {
        this.dataProgramada = dataProgramada;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public ItemSolicitacaoExame situacao(Situacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Exame getItemSolicitacaoExame() {
        return itemSolicitacaoExame;
    }

    public ItemSolicitacaoExame itemSolicitacaoExame(Exame exame) {
        this.itemSolicitacaoExame = exame;
        return this;
    }

    public void setItemSolicitacaoExame(Exame exame) {
        this.itemSolicitacaoExame = exame;
    }

    public SolicitacaoExame getSolicitacaoExame() {
        return solicitacaoExame;
    }

    public ItemSolicitacaoExame solicitacaoExame(SolicitacaoExame solicitacaoExame) {
        this.solicitacaoExame = solicitacaoExame;
        return this;
    }

    public void setSolicitacaoExame(SolicitacaoExame solicitacaoExame) {
        this.solicitacaoExame = solicitacaoExame;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemSolicitacaoExame)) {
            return false;
        }
        return id != null && id.equals(((ItemSolicitacaoExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemSolicitacaoExame{" +
            "id=" + getId() +
            ", urgente='" + isUrgente() + "'" +
            ", dataProgramada='" + getDataProgramada() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
