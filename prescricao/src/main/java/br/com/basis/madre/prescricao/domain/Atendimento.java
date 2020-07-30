package br.com.basis.madre.prescricao.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "madre-prescricao-atendimento")
public class Atendimento {

	private Long id;
	private String number;

	Atendimento() {

	}

	public Atendimento(String number) {
		super();
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
