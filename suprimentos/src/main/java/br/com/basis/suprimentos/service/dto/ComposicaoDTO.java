package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ComposicaoDTO implements Serializable {
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String servidor;


    private Long caracteristicaArmazenamentoId;
}
