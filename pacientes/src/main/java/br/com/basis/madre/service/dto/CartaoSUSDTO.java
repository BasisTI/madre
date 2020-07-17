package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.domain.CartaoSUS} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoSUSDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private DocumentoDeReferencia documentoDeReferencia;

    private String cartaoNacionalSaudeMae;

    private LocalDate dataDeEntradaNoBrasil;

    private LocalDate dataDeNaturalizacao;

    private String portaria;

    private Long justificativaId;

    private Long motivoDoCadastroId;


}
