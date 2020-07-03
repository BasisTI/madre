package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroDocumentosDTO implements Serializable {
    private Long id;

    private String numeroIdentidade;
    private OrgaoEmissorDTO orgaoEmissor;
    private UFDTO uf;
    private LocalDate dataDeEmissao;
    private String cpf;
    private String pisPasep;
    private String cnh;
    private LocalDate validadeCNH;


}
