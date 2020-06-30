package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Paciente} entity.
 */
public class PacienteInclusaoDTO implements Serializable {

    private Long id;

    private Long prontuario;

    @NotNull
    private String nome;

    private String nomeSocial;

    @NotNull
    private LocalDate dataDeNascimento;

    private Instant horaDeNascimento;

    private String email;

    private String observacao;

    @NotNull
    private GrauDeInstrucao grauDeInstrucao;

    @NotNull
    private Sexo sexo;

    private Set<TelefoneDTO> telefones = new HashSet<>();

    private Set<EnderecoDTO> enderecos = new HashSet<>();

    private GenitoresDTO genitores;

    private CartaoSUSDTO cartaoSUS;

    private ResponsavelDTO responsavel;

    private DocumentoDTO documento;

    private CertidaoDTO certidao;

    private Long ocupacaoId;

    private Long religiaoId;

    private Long naturalidadeId;

    private Long etniaId;

    private Long genitoresId;

    private Long nacionalidadeId;

    private Long racaId;

    private Long estadoCivilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProntuario() {
        return prontuario;
    }

    public void setProntuario(Long prontuario) {
        this.prontuario = prontuario;
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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Instant getHoraDeNascimento() {
        return horaDeNascimento;
    }

    public void setHoraDeNascimento(Instant horaDeNascimento) {
        this.horaDeNascimento = horaDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public GrauDeInstrucao getGrauDeInstrucao() {
        return grauDeInstrucao;
    }

    public void setGrauDeInstrucao(GrauDeInstrucao grauDeInstrucao) {
        this.grauDeInstrucao = grauDeInstrucao;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Set<TelefoneDTO> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<TelefoneDTO> telefones) {
        this.telefones = telefones;
    }

    public Set<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public GenitoresDTO getGenitores() {
        return genitores;
    }

    public void setGenitores(GenitoresDTO genitores) {
        this.genitores = genitores;
    }

    public CartaoSUSDTO getCartaoSUS() {
        return cartaoSUS;
    }

    public void setCartaoSUS(CartaoSUSDTO cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
    }

    public ResponsavelDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelDTO responsavel) {
        this.responsavel = responsavel;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
    }

    public CertidaoDTO getCertidao() {
        return certidao;
    }

    public void setCertidao(CertidaoDTO certidao) {
        this.certidao = certidao;
    }

    public Long getOcupacaoId() {
        return ocupacaoId;
    }

    public void setOcupacaoId(Long ocupacaoId) {
        this.ocupacaoId = ocupacaoId;
    }

    public Long getReligiaoId() {
        return religiaoId;
    }

    public void setReligiaoId(Long religiaoId) {
        this.religiaoId = religiaoId;
    }

    public Long getNaturalidadeId() {
        return naturalidadeId;
    }

    public void setNaturalidadeId(Long naturalidadeId) {
        this.naturalidadeId = naturalidadeId;
    }

    public Long getEtniaId() {
        return etniaId;
    }

    public void setEtniaId(Long etniaId) {
        this.etniaId = etniaId;
    }

    public Long getGenitoresId() {
        return genitoresId;
    }

    public void setGenitoresId(Long genitoresId) {
        this.genitoresId = genitoresId;
    }

    public Long getNacionalidadeId() {
        return nacionalidadeId;
    }

    public void setNacionalidadeId(Long nacionalidadeId) {
        this.nacionalidadeId = nacionalidadeId;
    }

    public Long getRacaId() {
        return racaId;
    }

    public void setRacaId(Long racaId) {
        this.racaId = racaId;
    }

    public Long getEstadoCivilId() {
        return estadoCivilId;
    }

    public void setEstadoCivilId(Long estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteInclusaoDTO pacienteDTO = (PacienteInclusaoDTO) o;
        if (pacienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacienteInclusaoDTO{" +
            "id=" + id +
            ", prontuario=" + prontuario +
            ", nome='" + nome + '\'' +
            ", nomeSocial='" + nomeSocial + '\'' +
            ", dataDeNascimento=" + dataDeNascimento +
            ", horaDeNascimento=" + horaDeNascimento +
            ", email='" + email + '\'' +
            ", observacao='" + observacao + '\'' +
            ", grauDeInstrucao=" + grauDeInstrucao +
            ", sexo=" + sexo +
            ", telefones=" + telefones +
            ", enderecos=" + enderecos +
            ", genitores=" + genitores +
            ", cartaoSUS=" + cartaoSUS +
            ", responsavel=" + responsavel +
            ", documento=" + documento +
            ", certidao=" + certidao +
            ", ocupacaoId=" + ocupacaoId +
            ", religiaoId=" + religiaoId +
            ", naturalidadeId=" + naturalidadeId +
            ", etniaId=" + etniaId +
            ", genitoresId=" + genitoresId +
            ", nacionalidadeId=" + nacionalidadeId +
            ", racaId=" + racaId +
            ", estadoCivilId=" + estadoCivilId +
            '}';
    }
}
