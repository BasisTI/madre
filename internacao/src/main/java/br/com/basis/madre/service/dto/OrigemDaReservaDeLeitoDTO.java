package br.com.basis.madre.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrigemDaReservaDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

}
