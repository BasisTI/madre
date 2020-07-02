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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-almoxarifado")
public class Almoxarifado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "descricao", length = 120, nullable = false)
    private String descricao;

    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "dias_estoque")
    private Integer diasEstoque;

    @Column(name = "central")
    private Boolean central;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "calcula_media_ponderada", nullable = false)
    private Boolean calculaMediaPonderada;

    @Column(name = "bloqueia_entrada_transferencia")
    private Boolean bloqueiaEntradaTransferencia;

    @OneToMany(mappedBy = "almoxarifado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TempoPorClasse> temposPorClasses = new HashSet<>();

    @Column(name = "centro_de_atividade_id")
    private Long centroDeAtividadeId;

    @ManyToOne
    @JsonIgnoreProperties("almoxarifados")
    private CaracteristicaArmazenamento caracteristicaArmazenamento;

    @OneToMany(mappedBy = "almoxarifado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstoqueAlmoxarifado> estoques = new HashSet<>();
}
