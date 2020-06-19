package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Almoxarifado.
 */
@Entity
@Table(name = "almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "almoxarifado")
public class Almoxarifado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("almoxarifados")
    private CentroDeAtividade centroDeAtividade;

    @ManyToOne
    @JsonIgnoreProperties("almoxarifados")
    private CaracteristicaArmazenamento caracteristicaArmazenamento;

    @OneToMany(mappedBy = "almoxarifado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstoqueAlmoxarifado> estoques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Almoxarifado descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiasEstoque() {
        return diasEstoque;
    }

    public Almoxarifado diasEstoque(Integer diasEstoque) {
        this.diasEstoque = diasEstoque;
        return this;
    }

    public void setDiasEstoque(Integer diasEstoque) {
        this.diasEstoque = diasEstoque;
    }

    public Boolean isCentral() {
        return central;
    }

    public Almoxarifado central(Boolean central) {
        this.central = central;
        return this;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Almoxarifado ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isCalculaMediaPonderada() {
        return calculaMediaPonderada;
    }

    public Almoxarifado calculaMediaPonderada(Boolean calculaMediaPonderada) {
        this.calculaMediaPonderada = calculaMediaPonderada;
        return this;
    }

    public void setCalculaMediaPonderada(Boolean calculaMediaPonderada) {
        this.calculaMediaPonderada = calculaMediaPonderada;
    }

    public Boolean isBloqueiaEntradaTransferencia() {
        return bloqueiaEntradaTransferencia;
    }

    public Almoxarifado bloqueiaEntradaTransferencia(Boolean bloqueiaEntradaTransferencia) {
        this.bloqueiaEntradaTransferencia = bloqueiaEntradaTransferencia;
        return this;
    }

    public void setBloqueiaEntradaTransferencia(Boolean bloqueiaEntradaTransferencia) {
        this.bloqueiaEntradaTransferencia = bloqueiaEntradaTransferencia;
    }

    public Set<TempoPorClasse> getTemposPorClasses() {
        return temposPorClasses;
    }

    public Almoxarifado temposPorClasses(Set<TempoPorClasse> tempoPorClasses) {
        this.temposPorClasses = tempoPorClasses;
        return this;
    }

    public Almoxarifado addTemposPorClasse(TempoPorClasse tempoPorClasse) {
        this.temposPorClasses.add(tempoPorClasse);
        tempoPorClasse.setAlmoxarifado(this);
        return this;
    }

    public Almoxarifado removeTemposPorClasse(TempoPorClasse tempoPorClasse) {
        this.temposPorClasses.remove(tempoPorClasse);
        tempoPorClasse.setAlmoxarifado(null);
        return this;
    }

    public void setTemposPorClasses(Set<TempoPorClasse> tempoPorClasses) {
        this.temposPorClasses = tempoPorClasses;
    }

    public CentroDeAtividade getCentroDeAtividade() {
        return centroDeAtividade;
    }

    public Almoxarifado centroDeAtividade(CentroDeAtividade centroDeAtividade) {
        this.centroDeAtividade = centroDeAtividade;
        return this;
    }

    public void setCentroDeAtividade(CentroDeAtividade centroDeAtividade) {
        this.centroDeAtividade = centroDeAtividade;
    }

    public CaracteristicaArmazenamento getCaracteristicaArmazenamento() {
        return caracteristicaArmazenamento;
    }

    public Almoxarifado caracteristicaArmazenamento(CaracteristicaArmazenamento caracteristicaArmazenamento) {
        this.caracteristicaArmazenamento = caracteristicaArmazenamento;
        return this;
    }

    public void setCaracteristicaArmazenamento(CaracteristicaArmazenamento caracteristicaArmazenamento) {
        this.caracteristicaArmazenamento = caracteristicaArmazenamento;
    }

    public Set<EstoqueAlmoxarifado> getEstoques() {
        return estoques;
    }

    public Almoxarifado estoques(Set<EstoqueAlmoxarifado> estoqueAlmoxarifados) {
        this.estoques = estoqueAlmoxarifados;
        return this;
    }

    public Almoxarifado addEstoques(EstoqueAlmoxarifado estoqueAlmoxarifado) {
        this.estoques.add(estoqueAlmoxarifado);
        estoqueAlmoxarifado.setAlmoxarifado(this);
        return this;
    }

    public Almoxarifado removeEstoques(EstoqueAlmoxarifado estoqueAlmoxarifado) {
        this.estoques.remove(estoqueAlmoxarifado);
        estoqueAlmoxarifado.setAlmoxarifado(null);
        return this;
    }

    public void setEstoques(Set<EstoqueAlmoxarifado> estoqueAlmoxarifados) {
        this.estoques = estoqueAlmoxarifados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Almoxarifado)) {
            return false;
        }
        return id != null && id.equals(((Almoxarifado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Almoxarifado{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", diasEstoque=" + getDiasEstoque() +
            ", central='" + isCentral() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", calculaMediaPonderada='" + isCalculaMediaPonderada() + "'" +
            ", bloqueiaEntradaTransferencia='" + isBloqueiaEntradaTransferencia() + "'" +
            "}";
    }
}
