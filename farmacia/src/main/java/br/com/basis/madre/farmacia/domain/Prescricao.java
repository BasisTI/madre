package br.com.basis.madre.farmacia.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "prescricao")
public class Prescricao {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String descricao;

    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Text)
    private String farmacia;

    @Field(type = FieldType.Text)
    private String unidade;
    


    public Prescricao(String descricao, String nome, String farmacia, String unidade) {
        this.descricao = descricao;
        this.nome = nome;
        this.farmacia = farmacia;
        this.unidade = unidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
