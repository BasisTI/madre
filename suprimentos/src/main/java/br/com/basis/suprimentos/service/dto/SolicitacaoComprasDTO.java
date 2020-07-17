package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SolicitacaoComprasDTO implements Serializable {
    private Long id;

    @NotNull
    private Long numero;
}
