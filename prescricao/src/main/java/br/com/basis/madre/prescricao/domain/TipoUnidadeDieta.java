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
 * A TipoUnidadeDieta.
 */
@Entity
@Table(name = "tipo_unidade_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tipounidadedieta")
public class TipoUnidadeDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "descricao", length = 80, nullable = false)
    private String descricao;

    @NotNull
    @Size(max = 3)
    @Column(name = "sigla", length = 3, nullable = false)
    private String sigla;

    @OneToMany(mappedBy = "tipoUnidadeDieta")
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

    public TipoUnidadeDieta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public TipoUnidadeDieta sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<ItemPrescricaoDieta> getItemPrescricaoDietas() {
        return itemPrescricaoDietas;
    }

    public TipoUnidadeDieta itemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
        return this;
    }

    public TipoUnidadeDieta addItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.add(itemPrescricaoDieta);
        itemPrescricaoDieta.setTipoUnidadeDieta(this);
        return this;
    }

    public TipoUnidadeDieta removeItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.remove(itemPrescricaoDieta);
        itemPrescricaoDieta.setTipoUnidadeDieta(null);
        return this;
    }

    public void setItemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
    }
}
