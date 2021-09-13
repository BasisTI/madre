package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SolicitacaoExame.
 */
@Entity
@Table(name = "solicitacao_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "solicitacaoexame")
public class SolicitacaoExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSolicitacaoExame")
    @SequenceGenerator(name = "seqSolicitacaoExame")
    private Long id;

    @NotNull
    @Column(name = "info_clinica", nullable = false)
    private String infoClinica;

    @NotNull
    @Column(name = "uso_antimicrobianos_24_h", nullable = false)
    private Boolean usoAntimicrobianos24h;

    @NotNull
    @Column(name = "pedido_primeiro_exame", nullable = false)
    private Boolean pedidoPrimeiroExame;

    @OneToMany(mappedBy = "solicitacaoExame", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ItemSolicitacaoExame> solicitacaoExames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoClinica() {
        return infoClinica;
    }

    public SolicitacaoExame infoClinica(String infoClinica) {
        this.infoClinica = infoClinica;
        return this;
    }

    public void setInfoClinica(String infoClinica) {
        this.infoClinica = infoClinica;
    }

    public Boolean isUsoAntimicrobianos24h() {
        return usoAntimicrobianos24h;
    }

    public SolicitacaoExame usoAntimicrobianos24h(Boolean usoAntimicrobianos24h) {
        this.usoAntimicrobianos24h = usoAntimicrobianos24h;
        return this;
    }

    public void setUsoAntimicrobianos24h(Boolean usoAntimicrobianos24h) {
        this.usoAntimicrobianos24h = usoAntimicrobianos24h;
    }

    public Boolean isPedidoPrimeiroExame() {
        return pedidoPrimeiroExame;
    }

    public SolicitacaoExame pedidoPrimeiroExame(Boolean pedidoPrimeiroExame) {
        this.pedidoPrimeiroExame = pedidoPrimeiroExame;
        return this;
    }

    public void setPedidoPrimeiroExame(Boolean pedidoPrimeiroExame) {
        this.pedidoPrimeiroExame = pedidoPrimeiroExame;
    }

    public Set<ItemSolicitacaoExame> getSolicitacaoExames() {
        return new HashSet<>(this.solicitacaoExames);
    }

    public SolicitacaoExame solicitacaoExames(Set<ItemSolicitacaoExame> itemSolicitacaoExames) {
        this.solicitacaoExames = new HashSet<>(itemSolicitacaoExames);
        return this;
    }

    public SolicitacaoExame addSolicitacaoExame(ItemSolicitacaoExame itemSolicitacaoExame) {
        this.solicitacaoExames.add(itemSolicitacaoExame);
        itemSolicitacaoExame.setSolicitacaoExame(this);
        return this;
    }

    public SolicitacaoExame removeSolicitacaoExame(ItemSolicitacaoExame itemSolicitacaoExame) {
        this.solicitacaoExames.remove(itemSolicitacaoExame);
        itemSolicitacaoExame.setSolicitacaoExame(null);
        return this;
    }

    public void setSolicitacaoExames(Set<ItemSolicitacaoExame> itemSolicitacaoExames) {
        this.solicitacaoExames = new HashSet<>(itemSolicitacaoExames);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitacaoExame)) {
            return false;
        }
        return id != null && id.equals(((SolicitacaoExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitacaoExame{" +
            "id=" + getId() +
            ", infoClinica='" + getInfoClinica() + "'" +
            ", usoAntimicrobianos24h='" + isUsoAntimicrobianos24h() + "'" +
            ", pedidoPrimeiroExame='" + isPedidoPrimeiroExame() + "'" +
            "}";
    }
}
