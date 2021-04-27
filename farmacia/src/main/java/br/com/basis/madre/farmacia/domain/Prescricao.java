package br.com.basis.madre.farmacia.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(indexName = "madre-farmacia-prescricao")
public class Prescricao implements Serializable {

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

    @Field(type = FieldType.Date)
    private LocalDate dataInicio;

    @Field(type = FieldType.Date)
    private LocalDateTime dataFim;

    @Field(type = FieldType.Text)
    private String local;

    public Prescricao(String nome, LocalDate dataInicio, LocalDateTime dataFim, String local) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
    }

    public Prescricao() {
    }




}
