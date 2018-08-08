package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;

public class AcaoTemp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idFuncionalidade;
    private String nmFuncionalidade;
    private String cdFuncionalidade;
    private String nmAcao;
    private String cdAcao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFuncionalidade() {
        return idFuncionalidade;
    }

    public void setIdFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
    }

    public String getNmFuncionalidade() {
        return nmFuncionalidade;
    }

    public void setNmFuncionalidade(String nmFuncionalidade) {
        this.nmFuncionalidade = nmFuncionalidade;
    }

    public String getCdFuncionalidade() {
        return cdFuncionalidade;
    }

    public void setCdFuncionalidade(String cdFuncionalidade) {
        this.cdFuncionalidade = cdFuncionalidade;
    }

    public String getNmAcao() {
        return nmAcao;
    }

    public void setNmAcao(String nmAcao) {
        this.nmAcao = nmAcao;
    }

    public String getCdAcao() {
        return cdAcao;
    }

    public void setCdAcao(String cdAcao) {
        this.cdAcao = cdAcao;
    }

}
