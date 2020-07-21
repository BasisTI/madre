package br.com.basis.madre.prescricao.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "madre-prescricao-cid")
@Data
@NoArgsConstructor
public class CID {

	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text)
	private String codigo;

	@Field(type = FieldType.Text)
	private String descricao;

}
