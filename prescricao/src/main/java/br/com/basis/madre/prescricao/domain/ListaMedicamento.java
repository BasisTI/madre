package br.com.basis.madre.prescricao.domain;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "lista-medicamento")
public class ListaMedicamento {
	
	@Id
	private Long id;
	
	@Field(type = FieldType.Text)
	private String nome;

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
	
	public ListaMedicamento() {}

	public ListaMedicamento(String nome) {
		super();
		this.nome = nome;
	}
	
	
	
	

}
