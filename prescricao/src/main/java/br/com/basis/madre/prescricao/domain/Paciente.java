package br.com.basis.madre.prescricao.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "paciente")
public class Paciente {
	
	@Id
	private Long id;
	
	@Field(type = FieldType.Text)
	private String nome;
	
	@Field(type = FieldType.Date)
	private LocalDate dataNascimento;
	
	@Field(type = FieldType.Text)
	private String prontuario;
	
	@Field(type = FieldType.Text)
	private String responsavel;
	
	@Field(type = FieldType.Date)
	private LocalDateTime dataAtendimento;
	public Paciente () {
		
	}
	
	public Paciente(String nome, LocalDate dataNascimento, String prontuario, String responsavel, LocalDateTime dataAtendimento) {
		
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.prontuario = prontuario;
		this.responsavel = responsavel;
		this.dataAtendimento = dataAtendimento;
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
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getProntuario() {
		return prontuario;
	}
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public LocalDateTime getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(LocalDateTime dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
