package br.com.basis.suprimentos.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CaracteristicaArmazenamento.
 */
@Entity
@Table(name = "caracteristica_armazenamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "caracteristicaarmazenamento")
public class CaracteristicaArmazenamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "mistura_materiais_direitos", nullable = false)
    private Boolean misturaMateriaisDireitos;

    @NotNull
    @Column(name = "mistura_grupo_materiais", nullable = false)
    private Boolean misturaGrupoMateriais;

    @OneToMany(mappedBy = "caracteristicaArmazenamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Composicao> composicoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMisturaMateriaisDireitos() {
        return misturaMateriaisDireitos;
    }

    public CaracteristicaArmazenamento misturaMateriaisDireitos(Boolean misturaMateriaisDireitos) {
        this.misturaMateriaisDireitos = misturaMateriaisDireitos;
        return this;
    }

    public void setMisturaMateriaisDireitos(Boolean misturaMateriaisDireitos) {
        this.misturaMateriaisDireitos = misturaMateriaisDireitos;
    }

    public Boolean isMisturaGrupoMateriais() {
        return misturaGrupoMateriais;
    }

    public CaracteristicaArmazenamento misturaGrupoMateriais(Boolean misturaGrupoMateriais) {
        this.misturaGrupoMateriais = misturaGrupoMateriais;
        return this;
    }

    public void setMisturaGrupoMateriais(Boolean misturaGrupoMateriais) {
        this.misturaGrupoMateriais = misturaGrupoMateriais;
    }

    public Set<Composicao> getComposicoes() {
        return composicoes;
    }

    public CaracteristicaArmazenamento composicoes(Set<Composicao> composicaos) {
        this.composicoes = composicaos;
        return this;
    }

    public CaracteristicaArmazenamento addComposicoes(Composicao composicao) {
        this.composicoes.add(composicao);
        composicao.setCaracteristicaArmazenamento(this);
        return this;
    }

    public CaracteristicaArmazenamento removeComposicoes(Composicao composicao) {
        this.composicoes.remove(composicao);
        composicao.setCaracteristicaArmazenamento(null);
        return this;
    }

    public void setComposicoes(Set<Composicao> composicaos) {
        this.composicoes = composicaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaracteristicaArmazenamento)) {
            return false;
        }
        return id != null && id.equals(((CaracteristicaArmazenamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CaracteristicaArmazenamento{" +
            "id=" + getId() +
            ", misturaMateriaisDireitos='" + isMisturaMateriaisDireitos() + "'" +
            ", misturaGrupoMateriais='" + isMisturaGrupoMateriais() + "'" +
            "}";
    }
}
