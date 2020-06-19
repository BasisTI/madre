package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A ItemPrescricaoDieta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-itemprescricaodieta")
public class ItemPrescricaoDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "quantidade", precision = 21, scale = 2)
    private BigDecimal quantidade;

    @Min(value = 0)
    @Column(name = "frequencia")
    private Integer frequencia;

    @Column(name = "numero_vezes")
    private Integer numeroVezes;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoItemDieta tipoItemDieta;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoAprazamento tipoAprazamento;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoUnidadeDieta tipoUnidadeDieta;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private PrescricaoDieta prescricaoDieta;


    public ItemPrescricaoDieta quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }


    public ItemPrescricaoDieta frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }


    public ItemPrescricaoDieta numeroVezes(Integer numeroVezes) {
        this.numeroVezes = numeroVezes;
        return this;
    }

    public ItemPrescricaoDieta tipoItemDieta(TipoItemDieta tipoItemDieta) {
        this.tipoItemDieta = tipoItemDieta;
        return this;
    }

    public ItemPrescricaoDieta tipoAprazamento(TipoAprazamento tipoAprazamento) {
        this.tipoAprazamento = tipoAprazamento;
        return this;
    }


    public ItemPrescricaoDieta tipoUnidadeDieta(TipoUnidadeDieta tipoUnidadeDieta) {
        this.tipoUnidadeDieta = tipoUnidadeDieta;
        return this;
    }


    public ItemPrescricaoDieta prescricaoDieta(PrescricaoDieta prescricaoDieta) {
        this.prescricaoDieta = prescricaoDieta;
        return this;
    }


}
