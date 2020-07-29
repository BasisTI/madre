package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = "lotes")
@Entity
@Table(name = "estoque_almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-estoquealmoxarifado")
public class EstoqueAlmoxarifado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Size(min = 1, max = 8)
    @Column(name = "endereco", length = 8)
    private String endereco;

    @Column(name = "quantidade_limite_armazenamento")
    private Long quantidadeLimiteArmazenamento;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_estoque_minimo", nullable = false)
    private Long quantidadeEstoqueMinimo;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_estoque_maximo", nullable = false)
    private Long quantidadeEstoqueMaximo;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_ponto_pedido", nullable = false)
    private Long quantidadePontoPedido;

    @Min(value = 0L)
    @Column(name = "quantidade_disponivel")
    private Long quantidadeDisponivel;

    @Min(value = 0L)
    @Column(name = "quantidade_em_uso")
    private Long quantidadeEmUso;

    @Column(name = "controla_validade")
    private Boolean controlaValidade;

    @Column(name = "estocavel")
    private Boolean estocavel;

    @NotNull
    @Min(value = 1)
    @Column(name = "tempo_reposicao", nullable = false)
    private Integer tempoReposicao;

    @OneToMany(mappedBy = "estoque", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lote> lotes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoques")
    private Almoxarifado almoxarifado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private Material material;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private Fornecedor fornecedor;

    @ManyToOne
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private SolicitacaoCompras solicitacaoCompras;
}
