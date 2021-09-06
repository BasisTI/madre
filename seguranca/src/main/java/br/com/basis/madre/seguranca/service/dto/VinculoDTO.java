package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Vinculo} entity.
 */
public class VinculoDTO extends DTOgenericoParaClassesComDescricao implements Serializable {

    @NotNull
    private Boolean situacao;

    private Boolean infDependente;

    private Boolean exTermino;

    private Boolean geraMatricula;

    private Boolean exCentroAtividade;

    private Boolean exOcupacao;

    private Boolean transfereStarh;

    private Boolean permiteDuplos;

    private Boolean exCpfRg;

    private Boolean gestapDesempenho;

    private Boolean emiteContrato;

    private String numerosDeDiasAdmissao;

    private String tituloMasculino;

    private String tituloFeminino;

    private Integer matricula;


    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Boolean isInfDependente() {
        return infDependente;
    }

    public void setInfDependente(Boolean infDependente) {
        this.infDependente = infDependente;
    }

    public Boolean isExTermino() {
        return exTermino;
    }

    public void setExTermino(Boolean exTermino) {
        this.exTermino = exTermino;
    }

    public Boolean isGeraMatricula() {
        return geraMatricula;
    }

    public void setGeraMatricula(Boolean geraMatricula) {
        this.geraMatricula = geraMatricula;
    }

    public Boolean isExCentroAtividade() {
        return exCentroAtividade;
    }

    public void setExCentroAtividade(Boolean exCentroAtividade) {
        this.exCentroAtividade = exCentroAtividade;
    }

    public Boolean isExOcupacao() {
        return exOcupacao;
    }

    public void setExOcupacao(Boolean exOcupacao) {
        this.exOcupacao = exOcupacao;
    }

    public Boolean isTransfereStarh() {
        return transfereStarh;
    }

    public void setTransfereStarh(Boolean transfereStarh) {
        this.transfereStarh = transfereStarh;
    }

    public Boolean isPermiteDuplos() {
        return permiteDuplos;
    }

    public void setPermiteDuplos(Boolean permiteDuplos) {
        this.permiteDuplos = permiteDuplos;
    }

    public Boolean isExCpfRg() {
        return exCpfRg;
    }

    public void setExCpfRg(Boolean exCpfRg) {
        this.exCpfRg = exCpfRg;
    }

    public Boolean isGestapDesempenho() {
        return gestapDesempenho;
    }

    public void setGestapDesempenho(Boolean gestapDesempenho) {
        this.gestapDesempenho = gestapDesempenho;
    }

    public Boolean isEmiteContrato() {
        return emiteContrato;
    }

    public void setEmiteContrato(Boolean emiteContrato) {
        this.emiteContrato = emiteContrato;
    }

    public String getNumerosDeDiasAdmissao() {
        return numerosDeDiasAdmissao;
    }

    public void setNumerosDeDiasAdmissao(String numerosDeDiasAdmissao) {
        this.numerosDeDiasAdmissao = numerosDeDiasAdmissao;
    }

    public String getTituloMasculino() {
        return tituloMasculino;
    }

    public void setTituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
    }

    public String getTituloFeminino() {
        return tituloFeminino;
    }

    public void setTituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VinculoDTO)) {
            return false;
        }

        return id != null && id.equals(((VinculoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VinculoDTO{" +
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
            ", matricula=" + getMatricula() +
            "}";
    }
}
