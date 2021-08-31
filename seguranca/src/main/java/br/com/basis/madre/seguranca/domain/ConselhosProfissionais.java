package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ConselhosProfissionais.
 */
@Entity
@Table(name = "conselhos_profissionais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-conselhosprofissionais")
public class ConselhosProfissionais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqConselhosProfissionais")
    @SequenceGenerator(name = "seqConselhosProfissionais")
    private Long id;

    @Column(name = "codigo")
    private Integer codigo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @Column(name = "titulo_masculino")
    private String tituloMasculino;

    @Column(name = "titulo_feminino")
    private String tituloFeminino;

    @Column(name = "situacao")
    private Boolean situacao;

    @OneToMany(mappedBy = "conselhosProfissionais")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Instituicao> tiposDeQualificacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public ConselhosProfissionais codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public ConselhosProfissionais nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public ConselhosProfissionais sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTituloMasculino() {
        return tituloMasculino;
    }

    public ConselhosProfissionais tituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
        return this;
    }

    public void setTituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
    }

    public String getTituloFeminino() {
        return tituloFeminino;
    }

    public ConselhosProfissionais tituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
        return this;
    }

    public void setTituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public ConselhosProfissionais situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Set<Instituicao> getTiposDeQualificacaos() {
        return tiposDeQualificacaos;
    }

    public ConselhosProfissionais tiposDeQualificacaos(Set<Instituicao> instituicaos) {
        this.tiposDeQualificacaos = instituicaos;
        return this;
    }

    public ConselhosProfissionais addTiposDeQualificacao(Instituicao instituicao) {
        this.tiposDeQualificacaos.add(instituicao);
        instituicao.setConselhosProfissionais(this);
        return this;
    }

    public ConselhosProfissionais removeTiposDeQualificacao(Instituicao instituicao) {
        this.tiposDeQualificacaos.remove(instituicao);
        instituicao.setConselhosProfissionais(null);
        return this;
    }

    public void setTiposDeQualificacaos(Set<Instituicao> instituicaos) {
        this.tiposDeQualificacaos = instituicaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConselhosProfissionais)) {
            return false;
        }
        return id != null && id.equals(((ConselhosProfissionais) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConselhosProfissionais{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", tituloMasculino='" + getTituloMasculino() + "'" +
            ", tituloFeminino='" + getTituloFeminino() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
