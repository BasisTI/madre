package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-material")
public class Material extends DomainAtivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMaterial")
    @SequenceGenerator(name = "seqMaterial")
    private Long id;

    @NotNull
    @Column(name = "coletavel", nullable = false)
    private Boolean coletavel;

    @NotNull
    @Column(name = "exige_informacao", nullable = false)
    private Boolean exigeInformacao;

    @NotNull
    @Column(name = "urina", nullable = false)
    private Boolean urina;

    @OneToMany(mappedBy = "material")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Recomendacao> materials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Material nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public Material ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public Boolean isColetavel() {
        return coletavel;
    }

    public Material coletavel(Boolean coletavel) {
        this.coletavel = coletavel;
        return this;
    }

    public void setColetavel(Boolean coletavel) {
        this.coletavel = coletavel;
    }

    public Boolean isExigeInformacao() {
        return exigeInformacao;
    }

    public Material exigeInformacao(Boolean exigeInformacao) {
        this.exigeInformacao = exigeInformacao;
        return this;
    }

    public void setExigeInformacao(Boolean exigeInformacao) {
        this.exigeInformacao = exigeInformacao;
    }

    public Boolean isUrina() {
        return urina;
    }

    public Material urina(Boolean urina) {
        this.urina = urina;
        return this;
    }

    public void setUrina(Boolean urina) {
        this.urina = urina;
    }

    public Set<Recomendacao> getMaterials() {
        return new HashSet<>(this.materials);
    }

    public Material materials(Set<Recomendacao> recomendacaos) {
        this.materials = new HashSet<>(recomendacaos);
        return this;
    }

    public Material addMaterial(Recomendacao recomendacao) {
        this.materials.add(recomendacao);
        recomendacao.setMaterial(this);
        return this;
    }

    public Material removeMaterial(Recomendacao recomendacao) {
        this.materials.remove(recomendacao);
        recomendacao.setMaterial(null);
        return this;
    }

    public void setMaterials(Set<Recomendacao> recomendacaos) {
        this.materials = new HashSet<>(recomendacaos);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        return id != null && id.equals(((Material) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", coletavel='" + isColetavel() + "'" +
            ", exigeInformacao='" + isExigeInformacao() + "'" +
            ", urina='" + isUrina() + "'" +
            "}";
    }
}
