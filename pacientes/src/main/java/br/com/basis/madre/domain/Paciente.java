package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "classificacao_de_risco", nullable = false)
    private Paciente classificacaoDeRisco;

    @Column(name = "nome_social")
    private String nomeSocial;

    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Column(name = "hora_de_nascimento")
    private Instant horaDeNascimento;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "observacao")
    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grau_de_instrucao", nullable = false)
    private GrauDeInstrucao grauDeInstrucao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @OneToOne
    @JoinColumn(unique = true)
    private CartaoSUS cartaoSUS;

    @OneToMany(mappedBy = "paciente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Telefone> telefones = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Endereco> enderecos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Responsavel responsavel;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Documento documento;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Certidao certidao;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Ocupacao ocupacao;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Religiao religiao;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Municipio naturalidade;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Etnia etnia;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Genitores genitores;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Nacionalidade nacionalidade;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Raca raca;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private EstadoCivil estadoCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Paciente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Paciente getClassificacaoDeRisco() {
        return classificacaoDeRisco;

    }

    public void setClassificacaoDeRisco(Paciente classificacaoDeRisco) {
        this.classificacaoDeRisco = classificacaoDeRisco;
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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public Paciente dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Instant getHoraDeNascimento() {
        return horaDeNascimento;
    }

    public Paciente horaDeNascimento(Instant horaDeNascimento) {
        this.horaDeNascimento = horaDeNascimento;
        return this;
    }

    public void setHoraDeNascimento(Instant horaDeNascimento) {
        this.horaDeNascimento = horaDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public Paciente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public Paciente observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public GrauDeInstrucao getGrauDeInstrucao() {
        return grauDeInstrucao;
    }

    public Paciente grauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
        return this;
    }

    public void setGrauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Paciente sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public CartaoSUS getCartaoSUS() {
        return cartaoSUS;
    }

    public Paciente cartaoSUS(CartaoSUS cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
        return this;
    }

    public void setCartaoSUS(CartaoSUS cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Paciente telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Paciente addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPaciente(this);
        return this;
    }

    public Paciente removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPaciente(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Paciente enderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    public Paciente addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setPaciente(this);
        return this;
    }

    public Paciente removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setPaciente(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public Paciente responsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Documento getDocumento() {
        return documento;
    }

    public Paciente documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Certidao getCertidao() {
        return certidao;
    }

    public Paciente certidao(Certidao certidao) {
        this.certidao = certidao;
        return this;
    }

    public void setCertidao(Certidao certidao) {
        this.certidao = certidao;
    }

    public Ocupacao getOcupacao() {
        return ocupacao;
    }

    public Paciente ocupacao(Ocupacao ocupacao) {
        this.ocupacao = ocupacao;
        return this;
    }

    public void setOcupacao(Ocupacao ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Religiao getReligiao() {
        return religiao;
    }

    public Paciente religiao(Religiao religiao) {
        this.religiao = religiao;
        return this;
    }

    public void setReligiao(Religiao religiao) {
        this.religiao = religiao;
    }

    public Municipio getNaturalidade() {
        return naturalidade;
    }

    public Paciente naturalidade(Municipio municipio) {
        this.naturalidade = municipio;
        return this;
    }

    public void setNaturalidade(Municipio municipio) {
        this.naturalidade = municipio;
    }

    public Etnia getEtnia() {
        return etnia;
    }

    public Paciente etnia(Etnia etnia) {
        this.etnia = etnia;
        return this;
    }

    public void setEtnia(Etnia etnia) {
        this.etnia = etnia;
    }

    public Genitores getGenitores() {
        return genitores;
    }

    public Paciente genitores(Genitores genitores) {
        this.genitores = genitores;
        return this;
    }

    public void setGenitores(Genitores genitores) {
        this.genitores = genitores;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public Paciente nacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Raca getRaca() {
        return raca;
    }

    public Paciente raca(Raca raca) {
        this.raca = raca;
        return this;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Paciente estadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeSocial='" + getNomeSocial() + "'" +
            ", dataDeNascimento='" + getDataDeNascimento() + "'" +
            ", horaDeNascimento='" + getHoraDeNascimento() + "'" +
            ", email='" + getEmail() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", grauDeInstrucao='" + getGrauDeInstrucao() + "'" +
            ", sexo='" + getSexo() + "'" +
            "}";
    }
}
