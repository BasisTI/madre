package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class FornecedorDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 11, max = 14)
    private String cpfCnpj;

    @NotNull
    @Size(max = 255)
    private String razaoSocial;

    @NotNull
    @Size(max = 120)
    private String nomeFantasia;
}
