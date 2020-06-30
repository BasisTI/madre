package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class DadosPessoaisDTO implements Serializable {
    private Long id;


    private String nome;
    private String nomeSocial;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private RacaDTO raca;
    private EtniaDTO etnia;
    private EstadoCivilDTO estadoCivil;
    private String prontuarioDaMae;
    private String nomeDaMae;
    private String nomeDoPai;
    private LocalDate dataDeNascimento;
    private Instant horaDoNascimento;
    private NacionalidadeDTO nacionalidade;
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    private GrauDeInstrucao grauDeInstrucao;
    private OcupacaoDTO ocupacao;
    private ReligiaoDTO religiao;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public RacaDTO getRaca() {
        return raca;
    }

    public void setRaca(RacaDTO raca) {
        this.raca = raca;
    }

    public EtniaDTO getEtnia() {
        return etnia;
    }

    public void setEtnia(EtniaDTO etnia) {
        this.etnia = etnia;
    }

    public EstadoCivilDTO getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilDTO estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProntuarioDaMae() {
        return prontuarioDaMae;
    }

    public void setProntuarioDaMae(String prontuarioDaMae) {
        this.prontuarioDaMae = prontuarioDaMae;
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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Instant getHoraDoNascimento() {
        return horaDoNascimento;
    }

    public void setHoraDoNascimento(Instant horaDoNascimento) {
        this.horaDoNascimento = horaDoNascimento;
    }

    public NacionalidadeDTO getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(NacionalidadeDTO nacionalidade) {
        this.nacionalidade = nacionalidade;
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

    public OcupacaoDTO getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(OcupacaoDTO ocupacao) {
        this.ocupacao = ocupacao;
    }

    public ReligiaoDTO getReligiao() {
        return religiao;
    }

    public void setReligiao(ReligiaoDTO religiao) {
        this.religiao = religiao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DadosPessoaisDTO)) return false;
        DadosPessoaisDTO that = (DadosPessoaisDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DadosPessoaisDTO{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", nomeSocial='" + nomeSocial + '\'' +
            ", sexo=" + sexo +
            ", raca=" + raca +
            ", etnia=" + etnia +
            ", estadoCivil=" + estadoCivil +
            ", prontuarioDaMae='" + prontuarioDaMae + '\'' +
            ", nomeDaMae='" + nomeDaMae + '\'' +
            ", nomeDoPai='" + nomeDoPai + '\'' +
            ", dataDeNascimento=" + dataDeNascimento +
            ", horaDoNascimento=" + horaDoNascimento +
            ", nacionalidade=" + nacionalidade +
            ", naturalidade='" + naturalidade + '\'' +
            ", grauDeInstrucao=" + grauDeInstrucao +
            ", ocupacao=" + ocupacao +
            ", religiao=" + religiao +
            ", email='" + email + '\'' +
            '}';
    }
}
