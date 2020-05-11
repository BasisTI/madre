package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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

    public UFDTO getUf() {
        return uf;
    }

    public void setUf(UFDTO uf) {
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIdentidade() {
        return numeroIdentidade;
    }

    public void setNumeroIdentidade(String numeroIdentidade) {
        this.numeroIdentidade = numeroIdentidade;
    }

    public OrgaoEmissorDTO getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(OrgaoEmissorDTO orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public LocalDate getValidadeCNH() {
        return validadeCNH;
    }

    public void setValidadeCNH(LocalDate validadeCNH) {
        this.validadeCNH = validadeCNH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastroDocumentosDTO)) return false;
        CadastroDocumentosDTO that = (CadastroDocumentosDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CadastroDocumentosDTO{" +
            "id=" + id +
            ", numeroIdentidade='" + numeroIdentidade + '\'' +
            ", orgaoEmissor=" + orgaoEmissor +
            ", uf=" + uf +
            ", dataDeEmissao=" + dataDeEmissao +
            ", cpf='" + cpf + '\'' +
            ", pisPasep='" + pisPasep + '\'' +
            ", cnh='" + cnh + '\'' +
            ", validadeCNH=" + validadeCNH +
            '}';
    }
}
