package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Vinculo.
 */
@Entity
@Table(name = "vinculo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vinculo")
public class Vinculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqVinculo")
    @SequenceGenerator(name = "seqVinculo")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "situacao", nullable = false)
    private Boolean situacao;

    @Column(name = "inf_dependente")
    private Boolean infDependente;

    @Column(name = "ex_termino")
    private Boolean exTermino;

    @Column(name = "gera_matricula")
    private Boolean geraMatricula;

    @Column(name = "ex_centro_atividade")
    private Boolean exCentroAtividade;

    @Column(name = "ex_ocupacao")
    private Boolean exOcupacao;

    @Column(name = "transfere_starh")
    private Boolean transfereStarh;

    @Column(name = "permite_duplos")
    private Boolean permiteDuplos;

    @Column(name = "ex_cpf_rg")
    private Boolean exCpfRg;

    @Column(name = "gestap_desempenho")
    private Boolean gestapDesempenho;

    @Column(name = "emite_contrato")
    private Boolean emiteContrato;

    @Column(name = "numeros_de_dias_admissao")
    private String numerosDeDiasAdmissao;

    @Column(name = "titulo_masculino")
    private String tituloMasculino;

    @Column(name = "titulo_feminino")
    private String tituloFeminino;

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

    public Vinculo codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Vinculo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public Vinculo situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Boolean isInfDependente() {
        return infDependente;
    }

    public Vinculo infDependente(Boolean infDependente) {
        this.infDependente = infDependente;
        return this;
    }

    public void setInfDependente(Boolean infDependente) {
        this.infDependente = infDependente;
    }

    public Boolean isExTermino() {
        return exTermino;
    }

    public Vinculo exTermino(Boolean exTermino) {
        this.exTermino = exTermino;
        return this;
    }

    public void setExTermino(Boolean exTermino) {
        this.exTermino = exTermino;
    }

    public Boolean isGeraMatricula() {
        return geraMatricula;
    }

    public Vinculo geraMatricula(Boolean geraMatricula) {
        this.geraMatricula = geraMatricula;
        return this;
    }

    public void setGeraMatricula(Boolean geraMatricula) {
        this.geraMatricula = geraMatricula;
    }

    public Boolean isExCentroAtividade() {
        return exCentroAtividade;
    }

    public Vinculo exCentroAtividade(Boolean exCentroAtividade) {
        this.exCentroAtividade = exCentroAtividade;
        return this;
    }

    public void setExCentroAtividade(Boolean exCentroAtividade) {
        this.exCentroAtividade = exCentroAtividade;
    }

    public Boolean isExOcupacao() {
        return exOcupacao;
    }

    public Vinculo exOcupacao(Boolean exOcupacao) {
        this.exOcupacao = exOcupacao;
        return this;
    }

    public void setExOcupacao(Boolean exOcupacao) {
        this.exOcupacao = exOcupacao;
    }

    public Boolean isTransfereStarh() {
        return transfereStarh;
    }

    public Vinculo transfereStarh(Boolean transfereStarh) {
        this.transfereStarh = transfereStarh;
        return this;
    }

    public void setTransfereStarh(Boolean transfereStarh) {
        this.transfereStarh = transfereStarh;
    }

    public Boolean isPermiteDuplos() {
        return permiteDuplos;
    }

    public Vinculo permiteDuplos(Boolean permiteDuplos) {
        this.permiteDuplos = permiteDuplos;
        return this;
    }

    public void setPermiteDuplos(Boolean permiteDuplos) {
        this.permiteDuplos = permiteDuplos;
    }

    public Boolean isExCpfRg() {
        return exCpfRg;
    }

    public Vinculo exCpfRg(Boolean exCpfRg) {
        this.exCpfRg = exCpfRg;
        return this;
    }

    public void setExCpfRg(Boolean exCpfRg) {
        this.exCpfRg = exCpfRg;
    }

    public Boolean isGestapDesempenho() {
        return gestapDesempenho;
    }

    public Vinculo gestapDesempenho(Boolean gestapDesempenho) {
        this.gestapDesempenho = gestapDesempenho;
        return this;
    }

    public void setGestapDesempenho(Boolean gestapDesempenho) {
        this.gestapDesempenho = gestapDesempenho;
    }

    public Boolean isEmiteContrato() {
        return emiteContrato;
    }

    public Vinculo emiteContrato(Boolean emiteContrato) {
        this.emiteContrato = emiteContrato;
        return this;
    }

    public void setEmiteContrato(Boolean emiteContrato) {
        this.emiteContrato = emiteContrato;
    }

    public String getNumerosDeDiasAdmissao() {
        return numerosDeDiasAdmissao;
    }

    public Vinculo numerosDeDiasAdmissao(String numerosDeDiasAdmissao) {
        this.numerosDeDiasAdmissao = numerosDeDiasAdmissao;
        return this;
    }

    public void setNumerosDeDiasAdmissao(String numerosDeDiasAdmissao) {
        this.numerosDeDiasAdmissao = numerosDeDiasAdmissao;
    }

    public String getTituloMasculino() {
        return tituloMasculino;
    }

    public Vinculo tituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
        return this;
    }

    public void setTituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
    }

    public String getTituloFeminino() {
        return tituloFeminino;
    }

    public Vinculo tituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
        return this;
    }

    public void setTituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vinculo)) {
            return false;
        }
        return id != null && id.equals(((Vinculo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vinculo{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", infDependente='" + isInfDependente() + "'" +
            ", exTermino='" + isExTermino() + "'" +
            ", geraMatricula='" + isGeraMatricula() + "'" +
            ", exCentroAtividade='" + isExCentroAtividade() + "'" +
            ", exOcupacao='" + isExOcupacao() + "'" +
            ", transfereStarh='" + isTransfereStarh() + "'" +
            ", permiteDuplos='" + isPermiteDuplos() + "'" +
            ", exCpfRg='" + isExCpfRg() + "'" +
            ", gestapDesempenho='" + isGestapDesempenho() + "'" +
            ", emiteContrato='" + isEmiteContrato() + "'" +
            ", numerosDeDiasAdmissao='" + getNumerosDeDiasAdmissao() + "'" +
            ", tituloMasculino='" + getTituloMasculino() + "'" +
            ", tituloFeminino='" + getTituloFeminino() + "'" +
            "}";
    }
}
