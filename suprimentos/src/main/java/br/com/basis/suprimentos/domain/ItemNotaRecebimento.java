package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_nota_recebimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-itemnotarecebimento")
public class ItemNotaRecebimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Max(value = 255L)
    @Column(name = "quantidade_receber", nullable = false)
    private Long quantidadeReceber;

    @NotNull
    @Size(max = 255)
    @Column(name = "quantidade_convertida", length = 255, nullable = false)
    private String quantidadeConvertida;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorTotal;


    @ManyToOne
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private Recebimento recebimento;


    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private MarcaComercial marcaComercial;


    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private Material material;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private UnidadeMedida unidadeMedida;
}
