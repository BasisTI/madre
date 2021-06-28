package br.com.basis.madre.domain;

import br.com.basis.madre.domain.validation.CartaoSUS;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;

/**
 * A Clinica.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "clinica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clinica")
public class Clinica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "capacidade_referencial")
    private Integer capacidadeReferencial;

    @CartaoSUS
    @Column(name = "numeroSUS")
    private String numeroSUS;

    @Column(name = "idade_minima_internacao")
    @Min(1L)
    private Integer idadeMinimaInternacao;

    @Column(name = "idade_maxima_internacao")
    @Max(50L)
    private Integer idadeMaximaInternacao;

    @Column(name = "idade_minima_ambulatorio")
    @Min(1L)
    private Integer idadeMinimaAmbulatorio;

    @Column(name = "idade_maxima_ambulatorio")
    @Max(50L)
    private Integer idadeMaximaAmbulatorio;

    public Clinica descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

}
