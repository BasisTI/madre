package br.com.basis.madre.pacientes.domain;

import br.com.basis.dynamicexports.pojo.ReportObject;
import br.com.basis.madre.pacientes.web.rest.util.MadreUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paciente")
public class Paciente implements Serializable,ReportObject {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "rg", length = 15, nullable = false)
    private String rg;

    @NotNull
    @CPF
    @Size(min = 11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Size(max = 1)
    @Column(name = "sexo", length = 1)
    private String sexo;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Size(min = 8, max = 10)
    @Column(name = "cep", length = 10, nullable = false)
    private String cep;


    @Size(max = 10)
    @Column(name = "prontuario", length = 10)
    private String prontuario;

    @Size(min = 1, max = 100)
    @Column(name = "nome_paciente", length = 100)
    private String nomePaciente;

    @Size(max = 100)
    @Column(name = "nome_social", length = 100)
    private String nomeSocial;

    @Size(max = 20)
    @Column(name = "raca_cor", length = 20)
    private String racaCor;

    @Size(max = 20)
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;

    @Size(max = 100)
    @Column(name = "nome_pai", length = 100)
    private String nomePai;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome_mae", length = 100, nullable = false)
    private String nomeMae;

    @Size(max = 50)
    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    @NotNull
    @Size(min = 15, max = 15)
    @Column(name = "cartao_sus", length = 15, nullable = false)
    private String cartaoSus;

    @Size(max = 100)
    @Column(name = "endereco", length = 100)
    private String endereco;

    @Size(max = 100)
    @Column(name = "complemento", length = 100)
    private String complemento;

    @Size(max = 200)
    @Column(name = "bairro", length = 200)
    private String bairro;

    @Size(max = 100)
    @Column(name = "cidade", length = 100)
    private String cidade;

    @Size(max = 2)
    @Column(name = "estado", length = 2)
    private String estado;


    @Size(max = 11)
    @Column(name = "telefone_principal", length = 11)
    private String telefonePrincipal;

    @Size(max = 11)
    @Column(name = "telefone_alternativo", length = 11)
    private String telefoneAlternativo;

    @Email
    @Size(max = 100)
    @Column(name = "email_principal", length = 100)
    private String emailPrincipal;

    @Email
    @Size(max = 100)
    @Column(name = "email_alternativo", length = 100)
    private String emailAlternativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRg() {
        return rg;
    }

    public Paciente rg(String rg) {
        this.rg = rg;
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public Paciente cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public Paciente sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Paciente dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCep() {
        return cep;
    }

    public Paciente cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getProntuario() {
        return prontuario;
    }

    public Paciente prontuario(String prontuario) {
        this.prontuario = prontuario;
        return this;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public Paciente nomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
        return this;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public Paciente nomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
        return this;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getRacaCor() {
        return racaCor;
    }

    public Paciente racaCor(String racaCor) {
        this.racaCor = racaCor;
        return this;
    }

    public void setRacaCor(String racaCor) {
        this.racaCor = racaCor;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public Paciente estadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNomePai() {
        return nomePai;
    }

    public Paciente nomePai(String nomePai) {
        this.nomePai = nomePai;
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public Paciente nomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public Paciente nacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public Paciente cartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
        return this;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public String getEndereco() {
        return endereco;
    }

    public Paciente endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public Paciente complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public Paciente bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public Paciente cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Paciente estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public Paciente telefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
        return this;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public Paciente telefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
        return this;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    public Paciente emailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
        return this;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public String getEmailAlternativo() {
        return emailAlternativo;
    }

    public Paciente emailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
        return this;
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paciente paciente = (Paciente) o;
        if (paciente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paciente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", rg='" + getRg() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", cep='" + getCep() + "'" +
            ", prontuario='" + getProntuario() + "'" +
            ", nomePaciente='" + getNomePaciente() + "'" +
            ", nomeSocial='" + getNomeSocial() + "'" +
            ", racaCor='" + getRacaCor() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", nacionalidade='" + getNacionalidade() + "'" +
            ", cartaoSus='" + getCartaoSus() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneAlternativo='" + getTelefoneAlternativo() + "'" +
            ", emailPrincipal='" + getEmailPrincipal() + "'" +
            ", emailAlternativo='" + getEmailAlternativo() + "'" +
            "}";

    }

    public String getDataNascimentoString(){
        String dataNascimentoString;
        dataNascimentoString = ObjectUtils.allNotNull(this.dataNascimento) ? MadreUtil.transformaLocalDateTimeEmString(this.dataNascimento) : null;
        return dataNascimentoString;
    }
}
