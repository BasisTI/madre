package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoItemDieta.
 */
@Entity
@Table(name = "tipo_item_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tipoitemdieta")
public class TipoItemDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

    @OneToMany(mappedBy = "tipoItemDieta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoDieta> itemPrescricaoDietas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoItemDieta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ItemPrescricaoDieta> getItemPrescricaoDietas() {
        return itemPrescricaoDietas;
    }

    public TipoItemDieta itemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
        return this;
    }

    public TipoItemDieta addItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.add(itemPrescricaoDieta);
        itemPrescricaoDieta.setTipoItemDieta(this);
        return this;
    }

    public TipoItemDieta removeItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.remove(itemPrescricaoDieta);
        itemPrescricaoDieta.setTipoItemDieta(null);
        return this;
    }

    public void setItemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoItemDieta)) {
            return false;
        }
        return id != null && id.equals(((TipoItemDieta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoItemDieta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
