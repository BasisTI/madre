package br.com.basis.madre.seguranca.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.Sexo;
import br.com.basis.madre.seguranca.domain.enumeration.EstadoCivil;
import br.com.basis.madre.seguranca.domain.enumeration.GrauDeInstrucao;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Pessoa} entity.
 */
public class PessoaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private String nome;

    @NotNull
    private String nomeDaMae;

    private String nomeDoPai;

    @NotNull
    private Sexo sexo;

    private EstadoCivil estadoCivil;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotNull
    private String nascionalidade;

    @NotNull
    private String naturalidade;

    private GrauDeInstrucao grauDeInstrucao;

    private String nomeUsual;

    
    private String email;

    private Long documentosId;

    private String documentosNumeroDaIdentidade;

    private Long cargoId;

    private String cargoDescricao;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNascionalidade() {
        return nascionalidade;
    }

    public void setNascionalidade(String nascionalidade) {
        this.nascionalidade = nascionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public GrauDeInstrucao getGrauDeInstrucao() {
        return grauDeInstrucao;
    }

    public void setGrauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
    }

    public String getNomeUsual() {
        return nomeUsual;
    }

    public void setNomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDocumentosId() {
        return documentosId;
    }

    public void setDocumentosId(Long documentosId) {
        this.documentosId = documentosId;
    }

    public String getDocumentosNumeroDaIdentidade() {
        return documentosNumeroDaIdentidade;
    }

    public void setDocumentosNumeroDaIdentidade(String documentosNumeroDaIdentidade) {
        this.documentosNumeroDaIdentidade = documentosNumeroDaIdentidade;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoDescricao() {
        return cargoDescricao;
    }

    public void setCargoDescricao(String cargoDescricao) {
        this.cargoDescricao = cargoDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaDTO)) {
            return false;
        }

        return id != null && id.equals(((PessoaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", nomeDaMae='" + getNomeDaMae() + "'" +
            ", nomeDoPai='" + getNomeDoPai() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", dataDeNascimento='" + getDataDeNascimento() + "'" +
            ", nascionalidade='" + getNascionalidade() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", grauDeInstrucao='" + getGrauDeInstrucao() + "'" +
            ", nomeUsual='" + getNomeUsual() + "'" +
            ", email='" + getEmail() + "'" +
            ", documentosId=" + getDocumentosId() +
            ", documentosNumeroDaIdentidade='" + getDocumentosNumeroDaIdentidade() + "'" +
            ", cargoId=" + getCargoId() +
            ", cargoDescricao='" + getCargoDescricao() + "'" +
            "}";
    }
}
