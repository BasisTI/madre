package br.com.basis.madre.farmacia.service.reindex;

public interface Indexador {

    void indexar();

    String getCodigo();

    void setCodigo(String codigo);

    String getDescricao();

    void setDescricao(String descricao);

}
