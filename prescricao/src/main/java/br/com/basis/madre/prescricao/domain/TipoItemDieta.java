package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A TipoItemDieta.
 */
@Data
@Entity
@Table(name = "tipo_item_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tipoitemdieta")
public class TipoItemDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

    @OneToMany(mappedBy = "tipoItemDieta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoDieta> itemPrescricaoDietas = new HashSet<>();


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
}
