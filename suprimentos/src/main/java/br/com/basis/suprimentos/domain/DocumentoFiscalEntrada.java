package br.com.basis.suprimentos.domain;

import br.com.basis.suprimentos.domain.enumeration.TipoDocumento;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumentoFiscal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "documento_fiscal_entrada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-documentofiscalentrada")
public class DocumentoFiscalEntrada implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
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
}
