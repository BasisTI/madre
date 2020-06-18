package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.basis.suprimentos.domain.enumeration.TipoDocumento;

import br.com.basis.suprimentos.domain.enumeration.TipoDocumentoFiscal;

/**
 * A DocumentoFiscalEntrada.
 */
@Entity
@Table(name = "documento_fiscal_entrada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documentofiscalentrada")
public class DocumentoFiscalEntrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_documento", nullable = false)
    private Long numeroDocumento;

    @NotNull
    @Size(max = 10)
    @Column(name = "serie", length = 10, nullable = false)
    private String serie;

    @Size(max = 120)
    @Column(name = "nota_empenho", length = 120)
    private String notaEmpenho;

    @NotNull
    @Size(min = 11, max = 14)
    @Column(name = "cpf_cnpj", length = 14, nullable = false, unique = true)
    private String cpfCnpj;

    @NotNull
    @Column(name = "data_geracao", nullable = false)
    private LocalDate dataGeracao;

    @NotNull
    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @NotNull
    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull
    @Column(name = "valor_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento_fiscal", nullable = false)
    private TipoDocumentoFiscal tipoDocumentoFiscal;

    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("documentoFiscalEntradas")
    private Fornecedor fornecedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public DocumentoFiscalEntrada numeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public DocumentoFiscalEntrada serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNotaEmpenho() {
        return notaEmpenho;
    }

    public DocumentoFiscalEntrada notaEmpenho(String notaEmpenho) {
        this.notaEmpenho = notaEmpenho;
        return this;
    }

    public void setNotaEmpenho(String notaEmpenho) {
        this.notaEmpenho = notaEmpenho;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public DocumentoFiscalEntrada cpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public DocumentoFiscalEntrada dataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
        return this;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public DocumentoFiscalEntrada dataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
        return this;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public DocumentoFiscalEntrada dataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
        return this;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public DocumentoFiscalEntrada dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public DocumentoFiscalEntrada valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public DocumentoFiscalEntrada tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoDocumentoFiscal getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public DocumentoFiscalEntrada tipoDocumentoFiscal(TipoDocumentoFiscal tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
        return this;
    }

    public void setTipoDocumentoFiscal(TipoDocumentoFiscal tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getObservacao() {
        return observacao;
    }

    public DocumentoFiscalEntrada observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public DocumentoFiscalEntrada fornecedor(Fornecedor fornecedor) {
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
        if (!(o instanceof DocumentoFiscalEntrada)) {
            return false;
        }
        return id != null && id.equals(((DocumentoFiscalEntrada) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentoFiscalEntrada{" +
            "id=" + getId() +
            ", numeroDocumento=" + getNumeroDocumento() +
            ", serie='" + getSerie() + "'" +
            ", notaEmpenho='" + getNotaEmpenho() + "'" +
            ", cpfCnpj='" + getCpfCnpj() + "'" +
            ", dataGeracao='" + getDataGeracao() + "'" +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataEntrada='" + getDataEntrada() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", tipoDocumentoFiscal='" + getTipoDocumentoFiscal() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
