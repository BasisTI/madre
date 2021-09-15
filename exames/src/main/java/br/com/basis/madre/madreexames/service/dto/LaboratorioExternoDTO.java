package br.com.basis.madre.madreexames.service.dto;

import br.com.basis.madre.madreexames.domain.enumeration.FormaEnvio;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.LaboratorioExterno} entity.
 */
public class LaboratorioExternoDTO extends DominioConvenioPlano implements Serializable {

    @NotNull
    private String sigla;

    @NotNull
    private String endereco;

    @NotNull
    private String municipio;

    @NotNull
    private Integer cep;

    @NotNull
    private Integer telefone;

    @NotNull
    private Integer fax;

    @NotNull
    private String email;

    @NotNull
    private String cgc;

    private FormaEnvio formaEnvio;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getFax() {
        return fax;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public FormaEnvio getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(FormaEnvio formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratorioExternoDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((LaboratorioExternoDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LaboratorioExternoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", cep=" + getCep() +
            ", telefone=" + getTelefone() +
            ", fax=" + getFax() +
            ", email='" + getEmail() + "'" +
            ", cgc='" + getCgc() + "'" +
            ", codigoConvenio='" + getCodigoConvenio() + "'" +
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            ", formaEnvio='" + getFormaEnvio() + "'" +
            "}";
    }
}
