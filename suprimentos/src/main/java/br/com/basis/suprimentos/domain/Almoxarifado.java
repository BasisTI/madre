package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "codigo", nullable = false)
    private Long codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "dias_estoque", nullable = false)
    private Integer diasEstoque;

    @NotNull
    @Column(name = "central", nullable = false)
    private Boolean central;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "calcula_media_ponderada", nullable = false)
    private Boolean calculaMediaPonderada;

    @NotNull
    @Column(name = "bloqueia_entrada_transferencia", nullable = false)
    private Boolean bloqueiaEntradaTransferencia;

    @Builder.Default
    @OneToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TempoPorClasse> temposPorClasses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("almoxarifados")
    private CentroDeAtividade centroDeAtividade;

    @ManyToOne
    @JsonIgnoreProperties("almoxarifados")
    private CaracteristicaArmazenamento caracteristicaArmazenamento;
}
