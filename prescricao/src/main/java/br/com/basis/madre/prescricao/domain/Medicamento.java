package br.com.basis.madre.prescricao.domain;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "medicamento")
public class Medicamento {
	
	@Id
	private Long id;
	
	@Field(type = FieldType.Text)
	private String nome;
	
	Medicamento(){
		
	}
	
	public Medicamento(String nome){
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return nome;
	}

	public void setName(String name) {
		this.nome = name;
	}
	
	

}


