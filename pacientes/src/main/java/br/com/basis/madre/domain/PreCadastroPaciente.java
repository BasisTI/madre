package br.com.basis.madre.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


 @Entity
@Table(name = "pre_cadastro_paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-precadastropaciente")
@Data
public class PreCadastroPaciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "nome", length = 40, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 20)
    @Column(name = "nome_social", length = 20, nullable = false)
    private String nomeSocial;

    @NotNull
    @Size(max = 40)
    @Column(name = "nome_da_mae", length = 40, nullable = false)
    private String nomeDaMae;

    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @NotNull
    @Size(max = 15)
    @Column(name = "cartao_sus", length = 15, nullable = false)
    private String cartaoSus;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "preCadastroPaciente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Triagem> triagem = new HashSet<>();

    @OneToOne
    private Paciente paciente;


     public PreCadastroPaciente nome(String nome) {
         this.nome = nome;
         return this;
     }

    public PreCadastroPaciente nomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
        return this;
    }

    public PreCadastroPaciente nomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public PreCadastroPaciente dataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

   public PreCadastroPaciente cartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
        return this;
    }


    public Boolean isStatus() {
        return status;
    }

    public PreCadastroPaciente status(Boolean status) {
        this.status = status;
        return this;
    }

    public PreCadastroPaciente triagem(Set<Triagem> triagem) {
        this.triagem = triagem;
        return this;
    }
}
