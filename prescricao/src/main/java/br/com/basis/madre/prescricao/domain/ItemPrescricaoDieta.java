package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ItemPrescricaoDieta.
 */
@Data
@Entity
@Table(name = "item_prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemprescricaodieta")
public class ItemPrescricaoDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "quantidade", precision = 21, scale = 2)
    private BigDecimal quantidade;

    @Min(value = 0)
    @Column(name = "frequencia")
    private Integer frequencia;

    @Column(name = "numero_vezes")
    private Integer numeroVezes;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoItemDieta tipoItemDieta;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoAprazamento tipoAprazamento;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoUnidadeDieta tipoUnidadeDieta;

    @ManyToOne
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
