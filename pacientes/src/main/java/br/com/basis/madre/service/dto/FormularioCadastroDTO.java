package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormularioCadastroDTO implements Serializable {

    private Long id;

    private DadosPessoaisDTO dadosPessoais;
    private List<TelefoneDTO> telefones;
    private List<EnderecoDTO> enderecos;
    private ResponsavelDTO responsavel;
    private CertidaoDTO certidao;
    private CadastroDocumentosDTO documentos;
    private CartaoSUSDTO cartaoSUS;
    private String observacao;


}
