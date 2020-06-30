package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.TipoDoEndereco;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Endereco} entity.
 */
public class EnderecoDTO implements Serializable {

    private Long id;

    @NotNull
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    private Boolean correspondencia;

    private TipoDoEndereco tipoDoEndereco;


    private Long municipioId;

    private Long pacienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Boolean isCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(Boolean correspondencia) {
        this.correspondencia = correspondencia;
    }

    public TipoDoEndereco getTipoDoEndereco() {
        return tipoDoEndereco;
    }

    public void setTipoDoEndereco(TipoDoEndereco tipoDoEndereco) {
        this.tipoDoEndereco = tipoDoEndereco;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnderecoDTO enderecoDTO = (EnderecoDTO) o;
        if (enderecoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enderecoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnderecoDTO{" +
            "id=" + getId() +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", correspondencia='" + isCorrespondencia() + "'" +
            ", tipoDoEndereco='" + getTipoDoEndereco() + "'" +
            ", municipioId=" + getMunicipioId() +
            ", pacienteId=" + getPacienteId() +
            "}";
    }
}
