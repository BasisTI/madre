package br.com.basis.madre.prescricao.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "madre-prescricao-medicamento")
@Data
public class Medicamento {
	
	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text)
	private String nome;

	public Medicamento() {

	}

	public Medicamento(String nome) {
		this.nome = nome;
	}

	
}
