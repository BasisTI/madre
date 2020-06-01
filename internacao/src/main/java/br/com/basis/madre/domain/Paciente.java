package br.com.basis.madre.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "madre-internacao-paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Field(type = FieldType.Text)
    private String nome;
}
