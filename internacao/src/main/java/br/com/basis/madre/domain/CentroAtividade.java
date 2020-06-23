package br.com.basis.madre.domain;


import lombok.Data;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;


@Data
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

}
