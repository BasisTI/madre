package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Composicao.
 */
@Entity
@Table(name = "composicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "composicao")
public class Composicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @NotNull
    @Column(name = "servidor", nullable = false)
    private String servidor;

    @ManyToOne
    @JsonIgnoreProperties("composicaos")
    private CaracteristicaArmazenamento caracteristicaArmazenamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Composicao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getServidor() {
        return servidor;
    }

    public Composicao servidor(String servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public CaracteristicaArmazenamento getCaracteristicaArmazenamento() {
        return caracteristicaArmazenamento;
    }

    public Composicao caracteristicaArmazenamento(CaracteristicaArmazenamento caracteristicaArmazenamento) {
        this.caracteristicaArmazenamento = caracteristicaArmazenamento;
        return this;
    }

    public void setCaracteristicaArmazenamento(CaracteristicaArmazenamento caracteristicaArmazenamento) {
        this.caracteristicaArmazenamento = caracteristicaArmazenamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Composicao)) {
            return false;
        }
        return id != null && id.equals(((Composicao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Composicao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", servidor='" + getServidor() + "'" +
            "}";
    }
}
