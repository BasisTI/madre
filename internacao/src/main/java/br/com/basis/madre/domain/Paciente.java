package br.com.basis.madre.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Document(indexName = "madre-internacao-paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String prontuario;

    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Date)
    private LocalDate dataDeNascimento;

}
