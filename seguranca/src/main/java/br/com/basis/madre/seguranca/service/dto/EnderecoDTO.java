package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.TipoDoEndereco;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Endereco} entity.
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

    private String municipioNome;

    private Long pessoaId;
    
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

    public String getMunicipioNome() {
        return municipioNome;
    }

    public void setMunicipioNome(String municipioNome) {
        this.municipioNome = municipioNome;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoDTO)) {
            return false;
        }

        return id != null && id.equals(((EnderecoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
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
            ", municipioNome='" + getMunicipioNome() + "'" +
            ", pessoaId=" + getPessoaId() +
            "}";
    }
}
