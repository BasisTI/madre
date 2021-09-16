package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.seguranca.domain.enumeration.Sexo;

import br.com.basis.madre.seguranca.domain.enumeration.EstadoCivil;

import br.com.basis.madre.seguranca.domain.enumeration.GrauDeInstrucao;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPessoa")
    @SequenceGenerator(name = "seqPessoa")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nome_da_mae", nullable = false)
    private String nomeDaMae;

    @Column(name = "nome_do_pai")
    private String nomeDoPai;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @NotNull
    @Column(name = "nascionalidade", nullable = false)
    private String nascionalidade;

    @NotNull
    @Column(name = "naturalidade", nullable = false)
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_de_instrucao")
    private GrauDeInstrucao grauDeInstrucao;

    @Column(name = "nome_usual")
    private String nomeUsual;


    @Column(name = "email", unique = true)
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private Documentos documentos;

    @OneToOne
    @JoinColumn(unique = true)
    private Cargo cargo;

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Endereco> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Telefone> telefones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Pessoa codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public Pessoa nomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public Pessoa nomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
        return this;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Pessoa sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Pessoa estadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public Pessoa dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNascionalidade() {
        return nascionalidade;
    }

    public Pessoa nascionalidade(String nascionalidade) {
        this.nascionalidade = nascionalidade;
        return this;
    }

    public void setNascionalidade(String nascionalidade) {
        this.nascionalidade = nascionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public Pessoa naturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
        return this;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public GrauDeInstrucao getGrauDeInstrucao() {
        return grauDeInstrucao;
    }

    public Pessoa grauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
        return this;
    }

    public void setGrauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
    }

    public String getNomeUsual() {
        return nomeUsual;
    }

    public Pessoa nomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
        return this;
    }

    public void setNomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Documentos getDocumentos() {
        return documentos;
    }

    public Pessoa documentos(Documentos documentos) {
        this.documentos = documentos;
        return this;
    }

    public void setDocumentos(Documentos documentos) {
        this.documentos = documentos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Pessoa cargo(Cargo cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Set<Endereco> getEnderecos() {
        return new HashSet<>(this.enderecos);
    }

    public Pessoa enderecos(Set<Endereco> enderecos) {
        this.enderecos = new HashSet<>(enderecos);
        return this;
    }

    public Pessoa addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setPessoa(this);
        return this;
    }

    public Pessoa removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setPessoa(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = new HashSet<>(enderecos);
    }

    public Set<Telefone> getTelefones() {
        return new HashSet<>(this.telefones);
    }

    public Pessoa telefones(Set<Telefone> telefones) {
        this.telefones = new HashSet<>(telefones);
        return this;
    }

    public Pessoa addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public Pessoa removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = new HashSet<>(telefones);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return id != null && id.equals(((Pessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoa{" +
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
            "}";
    }
}
