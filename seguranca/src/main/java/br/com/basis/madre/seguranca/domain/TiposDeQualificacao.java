package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.seguranca.domain.enumeration.TipoQualificacao;

/**
 * A TiposDeQualificacao.
 */
@Entity
@Table(name = "tipos_de_qualificacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-tiposdequalificacao")
public class TiposDeQualificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTiposDeQualificacao")
    @SequenceGenerator(name = "seqTiposDeQualificacao")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoQualificacao tipo;

    @Column(name = "conselho")
    private String conselho;

    @NotNull
    @Column(name = "situacao", nullable = false)
    private Boolean situacao;

    @OneToMany(mappedBy = "tiposDeQualificacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Graduacao> graduacaoQualificacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public TiposDeQualificacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoQualificacao getTipo() {
        return tipo;
    }

    public TiposDeQualificacao tipo(TipoQualificacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoQualificacao tipo) {
        this.tipo = tipo;
    }

    public String getConselho() {
        return conselho;
    }

    public TiposDeQualificacao conselho(String conselho) {
        this.conselho = conselho;
        return this;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public TiposDeQualificacao situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Set<Graduacao> getGraduacaoQualificacaos() {
        return new HashSet<>(graduacaoQualificacaos);
    }

    public TiposDeQualificacao graduacaoQualificacaos(Set<Graduacao> graduacaos) {
        this.graduacaoQualificacaos = new HashSet<>(graduacaos);
        return this;
    }

    public TiposDeQualificacao addGraduacaoQualificacao(Graduacao graduacao) {
        this.graduacaoQualificacaos.add(graduacao);
        graduacao.setTiposDeQualificacao(this);
        return this;
    }

    public TiposDeQualificacao removeGraduacaoQualificacao(Graduacao graduacao) {
        this.graduacaoQualificacaos.remove(graduacao);
        graduacao.setTiposDeQualificacao(null);
        return this;
    }

    public void setGraduacaoQualificacaos(Set<Graduacao> graduacaos) {
        this.graduacaoQualificacaos = new HashSet<>(graduacaos);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TiposDeQualificacao)) {
            return false;
        }
        return id != null && id.equals(((TiposDeQualificacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TiposDeQualificacao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", conselho='" + getConselho() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
