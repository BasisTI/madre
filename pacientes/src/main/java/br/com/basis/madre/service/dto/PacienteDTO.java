package br.com.basis.madre.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Paciente} entity.
 */
public class PacienteDTO implements Serializable {

    private Long id;

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

    private Long cartaoSUSId;

    private Long responsavelId;

    private Long documentoId;

    private Long certidaoId;

    private Long ocupacaoId;

    private Long religiaoId;

    private Long etniaId;

    private Long genitoresId;

    private Long nacionalidadeId;

    private Long municipioId;

    private Long racaId;

    private Long estadoCivilId;

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

    public Long getCartaoSUSId() {
        return cartaoSUSId;
    }

    public void setCartaoSUSId(Long cartaoSUSId) {
        this.cartaoSUSId = cartaoSUSId;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public Long getCertidaoId() {
        return certidaoId;
    }

    public void setCertidaoId(Long certidaoId) {
        this.certidaoId = certidaoId;
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

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;
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
        return "PacienteDTO{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", nomeSocial='" + nomeSocial + '\'' +
            ", dataDeNascimento=" + dataDeNascimento +
            ", horaDeNascimento=" + horaDeNascimento +
            ", email='" + email + '\'' +
            ", observacao='" + observacao + '\'' +
            ", grauDeInstrucao=" + grauDeInstrucao +
            ", sexo=" + sexo +
            ", cartaoSUSId=" + cartaoSUSId +
            ", responsavelId=" + responsavelId +
            ", documentoId=" + documentoId +
            ", certidaoId=" + certidaoId +
            ", ocupacaoId=" + ocupacaoId +
            ", religiaoId=" + religiaoId +
            ", etniaId=" + etniaId +
            ", genitoresId=" + genitoresId +
            ", nacionalidadeId=" + nacionalidadeId +
            ", municipioId=" + municipioId +
            ", racaId=" + racaId +
            ", estadoCivilId=" + estadoCivilId +
            '}';
    }
}
