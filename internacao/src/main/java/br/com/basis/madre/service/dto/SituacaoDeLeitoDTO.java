package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SituacaoDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    public SituacaoDeLeitoDTO id(Long id) {
        this.id = id;
        return this;
    }

}
