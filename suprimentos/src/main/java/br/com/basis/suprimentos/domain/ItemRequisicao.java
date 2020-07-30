package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@EqualsAndHashCode(exclude = "requisicao")
@Entity
@Table(name = "item_requisicao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-itemrequisicao")
public class ItemRequisicao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemRequisicao")
    @SequenceGenerator(name = "seqItemRequisicao")
    private Long id;

    @Min(0)
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Almoxarifado almoxarifado;

    @ManyToOne
    @JsonIgnoreProperties(value = "itens", allowSetters = true)
    private RequisicaoMaterial requisicao;
}
