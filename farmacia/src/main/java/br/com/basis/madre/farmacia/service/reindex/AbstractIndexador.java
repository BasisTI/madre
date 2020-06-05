package br.com.basis.madre.farmacia.service.reindex;

public abstract class AbstractIndexador implements Indexador {

    private String codigo;
    private String descricao;

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
