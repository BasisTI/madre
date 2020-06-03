package br.com.basis.madre.prescricao.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "madre-prescricao-paciente")
public class Paciente {
	
	private Long id;

    private String prontuario;

    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Date)
    private LocalDate dataDeNascimento;
	

}
