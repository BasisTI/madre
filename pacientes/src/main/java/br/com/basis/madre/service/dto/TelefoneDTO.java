package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.TipoDoContato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Telefone} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDTO implements Serializable {

    private Long id;

    @NotNull
    private String ddd;

    @NotNull
    private String numero;

    private TipoDoContato tipo;

    private String observacao;


}
