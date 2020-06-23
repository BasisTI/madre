package br.com.basis.suprimentos.service.dto;

import br.com.basis.suprimentos.domain.enumeration.TipoItemAf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AutorizacaoFornecimentoDTO implements Serializable {
    private Long id;

    @NotNull
    private Long numero;

    @NotNull
    @Size(max = 120)
    private String complemento;

    @NotNull
    private TipoItemAf tipoItem;

    private Long fornecedorId;

    private String fornecedorNome;
}
