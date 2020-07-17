package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Documento} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO implements Serializable {

    private Long id;

    private String numeroDaIdentidade;

    private LocalDate data;

    private String cpf;

    private String pisPasep;

    private String cnh;

    private LocalDate validadeDaCnh;

    private Boolean documentosApresentados;


    private Long orgaoEmissorId;

    private Long ufId;


}
