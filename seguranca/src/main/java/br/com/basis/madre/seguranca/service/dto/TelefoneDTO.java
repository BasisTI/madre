package br.com.basis.madre.seguranca.service.dto;

import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.TipoDoContato;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Telefone} entity.
 */
public class TelefoneDTO implements Serializable {
    
    private Long id;

    private String ddd;

    private String numero;

    private TipoDoContato tipo;

    private String observacao;

    private String ramal;


    private Long pessoaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoDoContato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDoContato tipo) {
        this.tipo = tipo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
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
        if (!(o instanceof TelefoneDTO)) {
            return false;
        }

        return id != null && id.equals(((TelefoneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id=" + getId() +
            ", ddd='" + getDdd() + "'" +
            ", numero='" + getNumero() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", ramal='" + getRamal() + "'" +
            ", pessoaId=" + getPessoaId() +
            "}";
    }
}
