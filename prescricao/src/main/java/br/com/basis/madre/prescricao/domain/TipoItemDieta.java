package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoItemDieta.
 */
@Data
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
