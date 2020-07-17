package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.TipoDoEndereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Endereco} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

    private Long id;

    @NotNull
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    private Boolean correspondencia;

    private TipoDoEndereco tipoDoEndereco;


    private Long municipioId;

    private Long pacienteId;


}
