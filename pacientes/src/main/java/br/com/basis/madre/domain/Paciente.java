package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * A Paciente.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Field(type = FieldType.Text)
    @Column(name = "nome_social")
    private String nomeSocial;

    @Field(type = FieldType.Date)
    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Field(type = FieldType.Date)
    @Column(name = "hora_de_nascimento")
    private Instant horaDeNascimento;

    @Email
    @Field(type = FieldType.Text)
    @Column(name = "email", unique = true)
    private String email;

    @Field(type = FieldType.Text)
    @Column(name = "observacao")
    private String observacao;

    @Field(type = FieldType.Keyword)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grau_de_instrucao", nullable = false)
    private GrauDeInstrucao grauDeInstrucao;

    @Field(type = FieldType.Keyword)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @Field(type = FieldType.Nested)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private CartaoSUS cartaoSUS;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "paciente_telefone",
        joinColumns = {@JoinColumn(name = "paciente_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "telefone_id", referencedColumnName = "id")}
    )
    private Set<Telefone> telefones = new HashSet<>();

    @Field(type = FieldType.Nested)
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Endereco> enderecos = new HashSet<>();

    @Field(type = FieldType.Nested)
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pacientes")
    private Responsavel responsavel;

    @Field(type = FieldType.Nested)
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pacientes")
    private Documento documento;

    @Field(type = FieldType.Nested)
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pacientes")
    private Certidao certidao;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Ocupacao ocupacao;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Religiao religiao;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private UF unidadeFederativa;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Municipio naturalidade;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Etnia etnia;

    @Field(type = FieldType.Nested)
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pacientes")
    private Genitores genitores;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Nacionalidade nacionalidade;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Raca raca;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private EstadoCivil estadoCivil;

    @OneToOne
    @JoinColumn(name = "pre_cadastro_paciente_id", referencedColumnName = "id")
    private PreCadastroPaciente preCadastroPaciente;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_num_prontuario")
    @SequenceGenerator(name = "seq_num_prontuario")
    @Field(type = FieldType.Text)
    @Column(name ="prontuario")
    private Long prontuario;

    public Paciente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Paciente nomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
        return this;
    }

    public Paciente dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    public Paciente horaDeNascimento(Instant horaDeNascimento) {
        this.horaDeNascimento = horaDeNascimento;
        return this;
    }

    public Paciente email(String email) {
        this.email = email;
        return this;
    }

    public Paciente observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public Paciente grauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
        return this;
    }

    public Paciente sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public Paciente cartaoSUS(CartaoSUS cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
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

    public Paciente responsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public Paciente documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public Paciente certidao(Certidao certidao) {
        this.certidao = certidao;
        return this;
    }

    public Paciente ocupacao(Ocupacao ocupacao) {
        this.ocupacao = ocupacao;
        return this;
    }


    public Paciente religiao(Religiao religiao) {
        this.religiao = religiao;
        return this;
    }

    public Paciente naturalidade(Municipio municipio) {
        this.naturalidade = municipio;
        return this;
    }

    public Paciente etnia(Etnia etnia) {
        this.etnia = etnia;
        return this;
    }

    public Paciente genitores(Genitores genitores) {
        this.genitores = genitores;
        return this;
    }

    public Paciente nacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public Paciente raca(Raca raca) {
        this.raca = raca;
        return this;
    }

    public Paciente estadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

}
