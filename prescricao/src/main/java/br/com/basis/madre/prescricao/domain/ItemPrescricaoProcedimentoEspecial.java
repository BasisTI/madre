package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
import lombok.Data;

/**
 * A ItemPrescricaoProcedimentoEspecial.
 */
@Data
@Entity
@Table(name = "item_prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "itemprescricaoprocedimentoespecial")
public class ItemPrescricaoProcedimentoEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Tipo do procedimento especial
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_procedimento")
    private TipoProcedimentoEspecial tipoProcedimento;

    /**
     * Quanditade da Ortese ou prótese, valor deve ser um inteiro
     */
    @Min(value = 0)
    @Column(name = "quantidade_ortese_protese")
    private Integer quantidadeOrteseProtese;

    /**
     * Informações complementares para o procedimento
     */
    @Size(max = 255)
    @Column(name = "informacoes", length = 255)
    private String informacoes;

    /**
     * Justificativa para o procedimento especial
     */
    @Size(max = 255)
    @Column(name = "justificativa", length = 255)
    private String justificativa;

    /**
     * Duração do procedimento solicitado
     */
    @Min(value = 0)
    @Column(name = "duracao_solicitada")
    private Integer duracaoSolicitada;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private EspeciaisDiversos especiaisDiversos;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private CirurgiasLeito cirurgiasLeito;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private OrteseProtese orteseProtese;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial;


    public ItemPrescricaoProcedimentoEspecial tipoProcedimento(TipoProcedimentoEspecial tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
        return this;
    }


    public ItemPrescricaoProcedimentoEspecial quantidadeOrteseProtese(Integer quantidadeOrteseProtese) {
        this.quantidadeOrteseProtese = quantidadeOrteseProtese;
        return this;
    }

    public ItemPrescricaoProcedimentoEspecial informacoes(String informacoes) {
        this.informacoes = informacoes;
        return this;
    }

    public ItemPrescricaoProcedimentoEspecial justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }


    public ItemPrescricaoProcedimentoEspecial duracaoSolicitada(Integer duracaoSolicitada) {
        this.duracaoSolicitada = duracaoSolicitada;
        return this;
    }


    public ItemPrescricaoProcedimentoEspecial especiaisDiversos(EspeciaisDiversos especiaisDiversos) {
        this.especiaisDiversos = especiaisDiversos;
        return this;
    }


    public ItemPrescricaoProcedimentoEspecial cirurgiasLeito(CirurgiasLeito cirurgiasLeito) {
        this.cirurgiasLeito = cirurgiasLeito;
        return this;
    }


    public ItemPrescricaoProcedimentoEspecial orteseProtese(OrteseProtese orteseProtese) {
        this.orteseProtese = orteseProtese;
        return this;
    }

    public ItemPrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial(PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial) {
        this.prescricaoProcedimentoEspecial = prescricaoProcedimentoEspecial;
        return this;
    }

}
