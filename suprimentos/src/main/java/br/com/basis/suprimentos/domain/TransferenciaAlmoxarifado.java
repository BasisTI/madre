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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "transferencia_almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-transferenciaalmoxarifado")
public class TransferenciaAlmoxarifado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transferenciaAlmoxarifados")
    private Almoxarifado origem;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transferenciaAlmoxarifados")
    private Almoxarifado destino;

    @ManyToOne
    @JsonIgnoreProperties("transferenciaAlmoxarifados")
    private InformacaoTransferencia informacaoTransferencia;

    @ManyToMany
    @JoinTable(
        name = "transferencia_almoxarifado_item",
        joinColumns = @JoinColumn(name = "transferencia_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<ItemTransferencia> itens = new HashSet<>();

    @Column(name = "gerado_por")
    private String geradoPor;

    @Column(name = "gerado_em")
    private ZonedDateTime geradoEm;
}
