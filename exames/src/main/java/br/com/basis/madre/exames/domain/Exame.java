package br.com.basis.madre.exames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Exame.
 */
@Entity
@Table(name = "exame")
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqExame")
    @SequenceGenerator(name = "seqExame")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_usual")
    private String nomeUsual;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @ManyToOne
    @JsonIgnoreProperties(value = "exames", allowSetters = true)
    private Material exame;

    @ManyToOne
    @JsonIgnoreProperties(value = "exames", allowSetters = true)
    private TipoAmostra exame;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Exame nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsual() {
        return nomeUsual;
    }

    public Exame nomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
        return this;
    }

    public void setNomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
    }

    public String getSigla() {
        return sigla;
    }

    public Exame sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Material getExame() {
        return exame;
    }

    public Exame exame(Material material) {
        this.exame = material;
        return this;
    }

    public void setExame(Material material) {
        this.exame = material;
    }

    public TipoAmostra getExame() {
        return exame;
    }

    public Exame exame(TipoAmostra tipoAmostra) {
        this.exame = tipoAmostra;
        return this;
    }

    public void setExame(TipoAmostra tipoAmostra) {
        this.exame = tipoAmostra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exame)) {
            return false;
        }
        return id != null && id.equals(((Exame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exame{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeUsual='" + getNomeUsual() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
