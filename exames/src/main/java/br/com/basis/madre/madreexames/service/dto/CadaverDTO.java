package br.com.basis.madre.madreexames.service.dto;

import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;
import br.com.basis.madre.madreexames.domain.enumeration.GrupoSanguineo;
import br.com.basis.madre.madreexames.domain.enumeration.Raca;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Cadaver} entity.
 */
public class CadaverDTO extends DominioCodigo implements Serializable {

    @NotNull
    private LocalDate dataNascimento;

    private Raca raca;

    private GrupoSanguineo grupoSanguineo;

    @NotNull
    private LocalDate dataRemocao;

    @NotNull
    private String causaObito;

    @NotNull
    private String realizadoPor;

    @NotNull
    private String lidoPor;

    @NotNull
    private String procedencia;

    @NotNull
    private String retirada;

    @NotNull
    private String codigoPlano;

    private ConvenioPlano convenioPlano;

    @NotNull
    private String observacao;

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public LocalDate getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getCausaObito() {
        return causaObito;
    }

    public void setCausaObito(String causaObito) {
        this.causaObito = causaObito;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getLidoPor() {
        return lidoPor;
    }

    public void setLidoPor(String lidoPor) {
        this.lidoPor = lidoPor;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getRetirada() {
        return retirada;
    }

    public void setRetirada(String retirada) {
        this.retirada = retirada;
    }

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public void setCodigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public ConvenioPlano getConvenioPlano() {
        return convenioPlano;
    }

    public void setConvenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CadaverDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((CadaverDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CadaverDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", raca='" + getRaca() + "'" +
            ", grupoSanguineo='" + getGrupoSanguineo() + "'" +
            ", dataRemocao='" + getDataRemocao() + "'" +
            ", causaObito='" + getCausaObito() + "'" +
            ", realizadoPor='" + getRealizadoPor() + "'" +
            ", lidoPor='" + getLidoPor() + "'" +
            ", procedencia='" + getProcedencia() + "'" +
            ", retirada='" + getRetirada() + "'" +
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
