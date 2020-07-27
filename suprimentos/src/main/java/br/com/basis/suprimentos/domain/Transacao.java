package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "transacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-transacao")
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTransacao")
    @SequenceGenerator(name = "seqTransacao")
    private Long id;

    @NotNull
    @Column(name = "quantidade_itens", nullable = false, updatable = false)
    private Long quantidadeItens;

    @ManyToOne
    @JsonIgnoreProperties(value = "transacaos", allowSetters = true)
    @JoinColumn(name = "tipo_transacao_id", updatable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "transacaos", allowSetters = true)
    @JoinColumn(name = "lancamento_id", updatable = false)
    private Lancamento lancamento;

    @ManyToOne
    @JoinColumn(name = "material_id", updatable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "almoxarifado_id", updatable = false)
    private Almoxarifado almoxarifado;
}
