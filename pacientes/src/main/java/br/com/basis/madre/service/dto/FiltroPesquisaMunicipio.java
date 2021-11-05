package br.com.basis.madre.service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroPesquisaMunicipio {

    private String ufId;
    private String nome;

    public boolean isEmpty() {
        return this.ufId == null && this.nome == null;
    }

}
