package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoMedica}
 * entity.
 */
@Data
@Document(indexName = "madre-prescricao-prescricaomedica", type = "prescricaomedica")

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "tipo")
@JsonSubTypes({
	  @Type(value = PrescricaoMedicamentoDTO.class, name = "MEDICAMENTO"),
	  @Type(value = PrescricaoDietaDTO.class, name = "DIETA"),
	  @Type(value = PrescricaoDiagnosticoDTO.class, name = "DIAGNOSTICO"),
	  @Type(value = PrescricaoProcedimentoDTO.class, name = "PROCEDIMENTO")
	})
public class PrescricaoMedicaDTO implements Serializable {


	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idLeito;

	private Long idUnidadeFuncional;

	private Long idAtendimento;

	private Long idPaciente;

	private String observacao;

	private LocalDate dataPrescricao;


}
