package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.domain.CEP} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CEPDTO implements Serializable {

    private Long id;

    private String cep;

    private String logradouro;

    private String bairro;

    private Long municipioId;

    private Long ufId;

}
