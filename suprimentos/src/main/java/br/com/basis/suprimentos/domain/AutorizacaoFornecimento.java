package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.suprimentos.domain.enumeration.TipoItemAf;

/**
 * A AutorizacaoFornecimento.
 */
@Entity
@Table(name = "autorizacao_fornecimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "autorizacaofornecimento")
public class AutorizacaoFornecimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numero;

    @NotNull
    @Size(max = 120)
    @Column(name = "complemento", length = 120, nullable = false)
    private String complemento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false)
    private TipoItemAf tipoItem;

    @ManyToOne
    @JsonIgnoreProperties("autorizacaoFornecimentos")
    private Fornecedor fornecedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public AutorizacaoFornecimento numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public AutorizacaoFornecimento complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoItemAf getTipoItem() {
        return tipoItem;
    }

    public AutorizacaoFornecimento tipoItem(TipoItemAf tipoItem) {
        this.tipoItem = tipoItem;
        return this;
    }

    public void setTipoItem(TipoItemAf tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public AutorizacaoFornecimento fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutorizacaoFornecimento)) {
            return false;
        }
        return id != null && id.equals(((AutorizacaoFornecimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutorizacaoFornecimento{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", complemento='" + getComplemento() + "'" +
            ", tipoItem='" + getTipoItem() + "'" +
            "}";
    }
}
