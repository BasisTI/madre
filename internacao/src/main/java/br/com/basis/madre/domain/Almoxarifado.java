package br.com.basis.madre.domain;


import lombok.Data;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "madre-almoxarifado")
public class Almoxarifado {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String nome;

    Almoxarifado(){

    }

    public Almoxarifado(String nome) {
        this.nome = nome;
    }

}
