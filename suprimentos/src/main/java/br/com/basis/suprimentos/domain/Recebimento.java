package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Recebimento.
 */
@Entity
@Table(name = "recebimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "recebimento")
public class Recebimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @OneToMany(mappedBy = "recebimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemNotaRecebimento> itensNotaRecebimentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("recebimentos")
    private DocumentoFiscalEntrada notaFiscalEntrada;

    @ManyToOne
    @JsonIgnoreProperties("recebimentos")
    private AutorizacaoFornecimento autorizacaoFornecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ItemNotaRecebimento> getItensNotaRecebimentos() {
        return itensNotaRecebimentos;
    }

    public Recebimento itensNotaRecebimentos(Set<ItemNotaRecebimento> itemNotaRecebimentos) {
        this.itensNotaRecebimentos = itemNotaRecebimentos;
        return this;
    }

    public Recebimento addItensNotaRecebimento(ItemNotaRecebimento itemNotaRecebimento) {
        this.itensNotaRecebimentos.add(itemNotaRecebimento);
        itemNotaRecebimento.setRecebimento(this);
        return this;
    }

    public Recebimento removeItensNotaRecebimento(ItemNotaRecebimento itemNotaRecebimento) {
        this.itensNotaRecebimentos.remove(itemNotaRecebimento);
        itemNotaRecebimento.setRecebimento(null);
        return this;
    }

    public void setItensNotaRecebimentos(Set<ItemNotaRecebimento> itemNotaRecebimentos) {
        this.itensNotaRecebimentos = itemNotaRecebimentos;
    }

    public DocumentoFiscalEntrada getNotaFiscalEntrada() {
        return notaFiscalEntrada;
    }

    public Recebimento notaFiscalEntrada(DocumentoFiscalEntrada documentoFiscalEntrada) {
        this.notaFiscalEntrada = documentoFiscalEntrada;
        return this;
    }

    public void setNotaFiscalEntrada(DocumentoFiscalEntrada documentoFiscalEntrada) {
        this.notaFiscalEntrada = documentoFiscalEntrada;
    }

    public AutorizacaoFornecimento getAutorizacaoFornecimento() {
        return autorizacaoFornecimento;
    }

    public Recebimento autorizacaoFornecimento(AutorizacaoFornecimento autorizacaoFornecimento) {
        this.autorizacaoFornecimento = autorizacaoFornecimento;
        return this;
    }

    public void setAutorizacaoFornecimento(AutorizacaoFornecimento autorizacaoFornecimento) {
        this.autorizacaoFornecimento = autorizacaoFornecimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recebimento)) {
            return false;
        }
        return id != null && id.equals(((Recebimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Recebimento{" +
            "id=" + getId() +
            "}";
    }
}
