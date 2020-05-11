package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.util.List;

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

    public List<TelefoneDTO> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneDTO> telefones) {
        this.telefones = telefones;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
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

    public CadastroDocumentosDTO getDocumentos() {
        return documentos;
    }

    public void setDocumentos(CadastroDocumentosDTO documentos) {
        this.documentos = documentos;
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
            ", telefones=" + telefones +
            ", enderecos=" + enderecos +
            ", responsavel=" + responsavel +
            ", certidao=" + certidao +
            ", documentos=" + documentos +
            ", cartaoSUS=" + cartaoSUS +
            ", observacao='" + observacao + '\'' +
            '}';
    }
}
