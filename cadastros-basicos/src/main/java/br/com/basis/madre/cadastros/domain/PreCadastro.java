package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PreCadastro.
 */
@Entity
@Table(name = "pre_cadastro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre", type="precadastro")
public class PreCadastro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome_do_paciente", length = 150, nullable = false)
    private String nomeDoPaciente;

    @Size(max = 150)
    @Column(name = "nome_social", length = 150)
    private String nomeSocial;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome_da_mae", length = 150, nullable = false)
    private String nomeDaMae;

    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Size(min = 15, max = 15)
    @Column(name = "n_cartao_sus", length = 15)
    private String numCartaoSus;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPaciente() {
        return nomeDoPaciente;
    }

    public PreCadastro nomeDoPaciente(String nomeDoPaciente) {
        this.nomeDoPaciente = nomeDoPaciente;
        return this;
    }

    public void setNomeDoPaciente(String nomeDoPaciente) {
        this.nomeDoPaciente = nomeDoPaciente;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public PreCadastro nomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
        return this;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }
    public PreCadastro nomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public PreCadastro dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNumCartaoSus() {
        return numCartaoSus;
    }

    public PreCadastro numCartaoSus(String numCartaoSus) {
        this.numCartaoSus = numCartaoSus;
        return this;
    }

    public void setNumCartaoSus(String numCartaoSus) {
        this.numCartaoSus = numCartaoSus;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public PreCadastro ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreCadastro preCadastro = (PreCadastro) o;
        if (preCadastro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preCadastro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreCadastro{" +
            "id=" + getId() +
            ", nomeDoPaciente='" + getNomeDoPaciente() + "'" +
            ", nomesocial='" + getNomeSocial() + "'" +
            ", nomeDaMae='" + getNomeDaMae() + "'" +
            ", dataDeNascimento='" + getDataDeNascimento() + "'" +
            ", numCartaoSus='" + getNumCartaoSus() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
