package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-material")
public class Material implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Size(max = 2000)
    @Column(name = "descricao", length = 2000)
    private String descricao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Size(max = 120)
    @Column(name = "regimento", length = 120)
    private String regimento;

    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;

    @Min(value = 0L)
    @Column(name = "unidade_id")
    private Long unidadeId;

    @Min(value = 0L)
    @Column(name = "procedimento_id")
    private Long procedimentoId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private UnidadeMedida unidadeMedida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private GrupoMaterial grupo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private Almoxarifado localEstoque;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private CodigoCatmat codigoCatmat;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private Sazonalidade sazonalidade;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private TipoResiduo tipoResiduo;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private OrigemParecerTecnico origemParecerTecnico;

    @OneToMany(mappedBy = "material")
    private Set<EstoqueGeral> estoquesGerais;
}
