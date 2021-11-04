package br.com.basis.madre.seguranca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Instituicao.
 */
@Entity
@Table(name = "instituicao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-instituicao")
public class Instituicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInstituicao")
    @SequenceGenerator(name = "seqInstituicao")
    private Long id;

    @Column(name = "codigo")
    private Integer codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "interno", nullable = false)
    private Boolean interno;

    @OneToMany(mappedBy = "instituicao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Graduacao> graduacaoInstituicaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "tiposDeQualificacaos", allowSetters = true)
    private ConselhosProfissionais conselhosProfissionais;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Instituicao codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instituicao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isInterno() {
        return interno;
    }

    public Instituicao interno(Boolean interno) {
        this.interno = interno;
        return this;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public Set<Graduacao> getGraduacaoInstituicaos() {
        return new HashSet<>(this.graduacaoInstituicaos);
    }

    public Instituicao graduacaoInstituicaos(Set<Graduacao> graduacaos) {
        this.graduacaoInstituicaos = new HashSet<>(graduacaos);
        return this;
    }

    public Instituicao addGraduacaoInstituicao(Graduacao graduacao) {
        this.graduacaoInstituicaos.add(graduacao);
        graduacao.setInstituicao(this);
        return this;
    }

    public Instituicao removeGraduacaoInstituicao(Graduacao graduacao) {
        this.graduacaoInstituicaos.remove(graduacao);
        graduacao.setInstituicao(null);
        return this;
    }

    public void setGraduacaoInstituicaos(Set<Graduacao> graduacaos) {
        this.graduacaoInstituicaos = new HashSet<>(graduacaos);
    }

    public ConselhosProfissionais getConselhosProfissionais() {
        return conselhosProfissionais;
    }

    public Instituicao conselhosProfissionais(ConselhosProfissionais conselhosProfissionais) {
        this.conselhosProfissionais = conselhosProfissionais;
        return this;
    }

    public void setConselhosProfissionais(ConselhosProfissionais conselhosProfissionais) {
        this.conselhosProfissionais = conselhosProfissionais;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instituicao)) {
            return false;
        }
        return id != null && id.equals(((Instituicao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instituicao{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", interno='" + isInterno() + "'" +
            "}";
    }
}
