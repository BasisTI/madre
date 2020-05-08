package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Medicamento.
 */
@Entity
@Table(name = "medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicamento")
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;


    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "concentracao")
    private String concentracao;

    @Column(name = "ativo")
    private Boolean ativo;

    @ManyToOne
    @JsonIgnoreProperties("medicamentos")
    private TipoMedicamento tipoMedicamento;

    @ManyToOne
    @JsonIgnoreProperties("medicamentos")
    private Apresentacao apresentacao;

    @ManyToOne
    @JsonIgnoreProperties("medicamentos")
    private Unidade unidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Medicamento codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Medicamento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Medicamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConcentracao() {
        return concentracao;
    }

    public Medicamento concentracao(String concentracao) {
        this.concentracao = concentracao;
        return this;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Medicamento ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoMedicamento getTipoMedicamento() {
        return tipoMedicamento;
    }

    public Medicamento tipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
        return this;
    }

    public void setTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public Apresentacao getApresentacao() {
        return apresentacao;
    }

    public Medicamento apresentacao(Apresentacao apresentacao) {
        this.apresentacao = apresentacao;
        return this;
    }

    public void setApresentacao(Apresentacao apresentacao) {
        this.apresentacao = apresentacao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Medicamento unidade(Unidade unidade) {
        this.unidade = unidade;
        return this;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicamento)) {
            return false;
        }
        return id != null && id.equals(((Medicamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", concentracao='" + getConcentracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
