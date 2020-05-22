package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numeroDoConselho;

    @NotNull
    private String nome;


    private Long especialidadeId;

}
