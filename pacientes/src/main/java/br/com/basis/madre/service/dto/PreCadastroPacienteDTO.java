package br.com.basis.madre.service.dto;
import lombok.Data;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;


@Data
public class PreCadastroPacienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String nome;

    @NotNull
    @Size(max = 20)
    private String nomeSocial;

    @NotNull
    @Size(max = 40)
    private String nomeDaMae;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotNull
    @Size(max = 20)
    private String cartaoSus;

    private Boolean ativo;
}
