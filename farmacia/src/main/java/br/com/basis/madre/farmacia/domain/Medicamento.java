package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Medicamento.
 */
@Data
@Entity
@Table(name = "medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-farmacia-medicamento")
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "codigo")
    private String codigo;

    @Field(type = FieldType.Text)
    @Column(name = "nome")
    private String nome;

    @Field(type = FieldType.Text)
    @Column(name = "descricao")
    private String descricao;

    @Field(type = FieldType.Text)
    @Column(name = "concentracao")
    private String concentracao;

    @Field(type = FieldType.Boolean)
    @Column(name = "ativo")
    private Boolean ativo;


    @ManyToOne
    @Field(type = FieldType.Object)
    @JsonIgnoreProperties("medicamentos")
    private TipoMedicamento tipoMedicamento;

    @Field(type = FieldType.Object)
    @ManyToOne
    @JsonIgnoreProperties("medicamentos")
    private Apresentacao apresentacao;

    @Field(type = FieldType.Object)
    @ManyToOne
    @JsonIgnoreProperties("medicamentos")
    private Unidade unidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Medicamento codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }



    public Medicamento nome(String nome) {
        this.nome = nome;
        return this;
    }



    public Medicamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }



    public Medicamento concentracao(String concentracao) {
        this.concentracao = concentracao;
        return this;
    }



    public Boolean isAtivo() {
        return ativo;
    }

    public Medicamento ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }


    public Medicamento tipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
        return this;
    }



    public Medicamento apresentacao(Apresentacao apresentacao) {
        this.apresentacao = apresentacao;
        return this;
    }



    public Medicamento unidade(Unidade unidade) {
        this.unidade = unidade;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


}
