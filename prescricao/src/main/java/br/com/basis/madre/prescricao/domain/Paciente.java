package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "madre-prescricao-paciente")
public class Paciente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	@Field(type = FieldType.Text)
	private String prontuario;
	
    @Field(type = FieldType.Text)
    
    private String nome;

    @Field(type = FieldType.Date)
    private LocalDate dataDeNascimento;
	

}
