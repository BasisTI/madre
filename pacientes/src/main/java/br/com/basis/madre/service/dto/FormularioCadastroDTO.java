package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.util.List;

public class FormularioCadastroDTO implements Serializable {

    private Long id;

    private DadosPessoaisDTO dadosPessoais;
    private List<TelefoneDTO> telefones;
    private List<EnderecoDTO> endereco;
    private ResponsavelDTO responsavel;
    private CertidaoDTO certidao;
    private DocumentoDTO documento;
    private CartaoSUSDTO cartaoSUS;
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosPessoaisDTO getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoaisDTO dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public List<TelefoneDTO> getTelefone() {
        return telefones;
    }

    public void setTelefone(List<TelefoneDTO> telefone) {
        this.telefones = telefone;
    }

    public List<EnderecoDTO> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<EnderecoDTO> endereco) {
        this.endereco = endereco;
    }

    public ResponsavelDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelDTO responsavel) {
        this.responsavel = responsavel;
    }

    public CertidaoDTO getCertidao() {
        return certidao;
    }

    public void setCertidao(CertidaoDTO certidao) {
        this.certidao = certidao;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
    }

    public CartaoSUSDTO getCartaoSUS() {
        return cartaoSUS;
    }

    public void setCartaoSUS(CartaoSUSDTO cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "FormularioCadastroDTO{" +
            "dadosPessoais=" + dadosPessoais +
            ", telefone=" + telefones +
            ", endereco=" + endereco +
            ", responsavel=" + responsavel +
            ", certidao=" + certidao +
            ", documento=" + documento +
            ", cartaoSUS=" + cartaoSUS +
            ", observacao='" + observacao + '\'' +
            '}';
    }
}
