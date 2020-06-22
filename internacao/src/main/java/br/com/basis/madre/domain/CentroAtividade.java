package br.com.basis.madre.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "madre-centro-de-atividade")
public class CentroAtividade {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String nome;

    CentroAtividade() {
    }

    public CentroAtividade(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}