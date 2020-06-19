package br.com.basis.suprimentos.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumento;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumentoFiscal;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.DocumentoFiscalEntrada} entity.
 */
public class DocumentoFiscalEntradaDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numeroDocumento;

    @NotNull
    @Size(max = 10)
    private String serie;

    @Size(max = 120)
    private String notaEmpenho;

    @NotNull
    @Size(min = 11, max = 14)
    private String cpfCnpj;

    @NotNull
    private LocalDate dataGeracao;

    @NotNull
    private LocalDate dataEmissao;

    @NotNull
    private LocalDate dataEntrada;

    @NotNull
    private LocalDate dataVencimento;

    @NotNull
    private BigDecimal valorTotal;

    private TipoDocumento tipoDocumento;

    @NotNull
    private TipoDocumentoFiscal tipoDocumentoFiscal;

    @Size(max = 500)
    private String observacao;


    private Long fornecedorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNotaEmpenho() {
        return notaEmpenho;
    }

    public void setNotaEmpenho(String notaEmpenho) {
        this.notaEmpenho = notaEmpenho;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoDocumentoFiscal getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(TipoDocumentoFiscal tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = (DocumentoFiscalEntradaDTO) o;
        if (documentoFiscalEntradaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentoFiscalEntradaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentoFiscalEntradaDTO{" +
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
            ", fornecedor=" + getFornecedorId() +
            "}";
    }
}
