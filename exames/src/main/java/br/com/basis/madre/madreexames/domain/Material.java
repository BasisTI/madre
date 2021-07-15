package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMaterial")
    @SequenceGenerator(name = "seqMaterial")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

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

    public String getNome() {
        return nome;
    }

    public Material nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Material codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Set<Recomendacao> getMaterials() {
        return materials;
    }

    public Material materials(Set<Recomendacao> recomendacaos) {
        this.materials = recomendacaos;
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
        this.materials = recomendacaos;
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
            ", codigo=" + getCodigo() +
            "}";
    }
}
