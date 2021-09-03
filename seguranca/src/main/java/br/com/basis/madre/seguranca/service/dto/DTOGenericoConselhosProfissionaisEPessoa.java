package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;

public class DTOGenericoConselhosProfissionaisEPessoa {

    Long id;

    private Integer codigo;

    @NotNull
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
