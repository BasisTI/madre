package br.com.basis.madre.prescricao.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "madre-prescricao-leito")
public class Leito {

	private Long id;

	private String nome;

	private Integer andar;

	private Integer ala;
	
	Leito(){
		
	}

	public Leito(String nome, Integer andar, Integer ala) {
		super();
		this.nome = nome;
		this.andar = andar;
		this.ala = ala;
	}

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

	public Integer getAndar() {
		return andar;
	}

	public void setAndar(Integer andar) {
		this.andar = andar;
	}

	public Integer getAla() {
		return ala;
	}

	public void setAla(Integer ala) {
		this.ala = ala;
	}

}
