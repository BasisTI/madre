package br.com.basis.madre.seguranca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;

import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.madre.seguranca.domain.enumeration.SituacaoGraduacao;

/**
 * A Graduacao.
 */
@Entity
@Table(name = "graduacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-graduacao")
public class Graduacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGraduacao")
    @SequenceGenerator(name = "seqGraduacao")
    private Long id;

    @Column(name = "curso")
    private String curso;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoGraduacao situacao;

    @NotNull
    @Column(name = "ano_inicio", nullable = false)
    private LocalDate anoInicio;

    @Column(name = "ano_fim")
    private LocalDate anoFim;

    @Column(name = "semestre")
    private String semestre;

    @Column(name = "nro_reg_conselho")
    private String nroRegConselho;

    @ManyToOne
    @JsonIgnoreProperties(value = "graduacaos", allowSetters = true)
    private Servidor servidor;

    @ManyToOne
    @JsonIgnoreProperties(value = "graduacaoQualificacaos", allowSetters = true)
    private TiposDeQualificacao tiposDeQualificacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "graduacaoInstituicaos", allowSetters = true)
    private Instituicao instituicao;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public Graduacao curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public SituacaoGraduacao getSituacao() {
        return situacao;
    }

    public Graduacao situacao(SituacaoGraduacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(SituacaoGraduacao situacao) {
        this.situacao = situacao;
    }

    public LocalDate getAnoInicio() {
        return anoInicio;
    }

    public Graduacao anoInicio(LocalDate anoInicio) {
        this.anoInicio = anoInicio;
        return this;
    }

    public void setAnoInicio(LocalDate anoInicio) {
        this.anoInicio = anoInicio;
    }

    public LocalDate getAnoFim() {
        return anoFim;
    }

    public Graduacao anoFim(LocalDate anoFim) {
        this.anoFim = anoFim;
        return this;
    }

    public void setAnoFim(LocalDate anoFim) {
        this.anoFim = anoFim;
    }

    public String getSemestre() {
        return semestre;
    }

    public Graduacao semestre(String semestre) {
        this.semestre = semestre;
        return this;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getNroRegConselho() {
        return nroRegConselho;
    }

    public Graduacao nroRegConselho(String nroRegConselho) {
        this.nroRegConselho = nroRegConselho;
        return this;
    }

    public void setNroRegConselho(String nroRegConselho) {
        this.nroRegConselho = nroRegConselho;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public Graduacao servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public TiposDeQualificacao getTiposDeQualificacao() {
        return tiposDeQualificacao;
    }

    public Graduacao tiposDeQualificacao(TiposDeQualificacao tiposDeQualificacao) {
        this.tiposDeQualificacao = tiposDeQualificacao;
        return this;
    }

    public void setTiposDeQualificacao(TiposDeQualificacao tiposDeQualificacao) {
        this.tiposDeQualificacao = tiposDeQualificacao;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public Graduacao instituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
        return this;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graduacao)) {
            return false;
        }
        return id != null && id.equals(((Graduacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Graduacao{" +
            "id=" + getId() +
            ", curso='" + getCurso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", anoInicio='" + getAnoInicio() + "'" +
            ", anoFim='" + getAnoFim() + "'" +
            ", semestre='" + getSemestre() + "'" +
            ", nroRegConselho='" + getNroRegConselho() + "'" +
            "}";
    }
}
