package br.com.basis.madre.madreexames.service.dto.integracao;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
public class ServidorDTO implements Serializable {

    private Long id;

    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }


}
