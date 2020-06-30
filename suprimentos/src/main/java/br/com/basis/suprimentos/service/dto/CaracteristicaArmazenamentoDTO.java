package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
public class CaracteristicaArmazenamentoDTO implements Serializable {
    private Long id;

    @NotNull
    private Boolean misturaMateriaisDireitos;

    @NotNull
    private Boolean misturaGrupoMateriais;
}
