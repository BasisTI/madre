package br.com.basis.madre.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
