package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EstoqueGeralDTO implements Serializable {
    private Long id;

    @NotNull
    private Double valor;

    @NotNull
    private Long quantidade;

    @NotNull
    private Long materialId;
}
