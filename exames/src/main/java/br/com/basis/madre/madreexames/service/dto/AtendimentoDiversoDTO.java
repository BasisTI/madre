package br.com.basis.madre.madreexames.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import br.com.basis.madre.madreexames.domain.enumeration.OrigemAmostra;
import br.com.basis.madre.madreexames.domain.enumeration.TipoAmostra;
import br.com.basis.madre.madreexames.domain.enumeration.Sexo;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.AtendimentoDiverso} entity.
 */
public class AtendimentoDiversoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private Integer unidadeExecutoraId;

    private OrigemAmostra origemAmostra;

    private TipoAmostra tipoAmostra;

    @NotNull
    private String identificacao;

    @NotNull
    private LocalDate dataSoro;

    @NotNull
    private String material;

    @NotNull
    private Integer especialidadeId;

    @NotNull
    private Integer centroAtividadeId;

    @NotNull
    private LocalDate dataNascimento;

    private Sexo sexo;


    private Long laboratorioId;

    private String laboratorioNome;

    private Long controleId;

    private String controleCodigo;

    private Long cadaverId;

    private String cadaverNome;

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

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public OrigemAmostra getOrigemAmostra() {
        return origemAmostra;
    }

    public void setOrigemAmostra(OrigemAmostra origemAmostra) {
        this.origemAmostra = origemAmostra;
    }

    public TipoAmostra getTipoAmostra() {
        return tipoAmostra;
    }

    public void setTipoAmostra(TipoAmostra tipoAmostra) {
        this.tipoAmostra = tipoAmostra;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public LocalDate getDataSoro() {
        return dataSoro;
    }

    public void setDataSoro(LocalDate dataSoro) {
        this.dataSoro = dataSoro;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Integer especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public Integer getCentroAtividadeId() {
        return centroAtividadeId;
    }

    public void setCentroAtividadeId(Integer centroAtividadeId) {
        this.centroAtividadeId = centroAtividadeId;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Long getLaboratorioId() {
        return laboratorioId;
    }

    public void setLaboratorioId(Long laboratorioExternoId) {
        this.laboratorioId = laboratorioExternoId;
    }

    public String getLaboratorioNome() {
        return laboratorioNome;
    }

    public void setLaboratorioNome(String laboratorioExternoNome) {
        this.laboratorioNome = laboratorioExternoNome;
    }

    public Long getControleId() {
        return controleId;
    }

    public void setControleId(Long controleQualidadeId) {
        this.controleId = controleQualidadeId;
    }

    public String getControleCodigo() {
        return controleCodigo;
    }

    public void setControleCodigo(String controleQualidadeCodigo) {
        this.controleCodigo = controleQualidadeCodigo;
    }

    public Long getCadaverId() {
        return cadaverId;
    }

    public void setCadaverId(Long cadaverId) {
        this.cadaverId = cadaverId;
    }

    public String getCadaverNome() {
        return cadaverNome;
    }

    public void setCadaverNome(String cadaverNome) {
        this.cadaverNome = cadaverNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtendimentoDiversoDTO)) {
            return false;
        }

        return id != null && id.equals(((AtendimentoDiversoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtendimentoDiversoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", origemAmostra='" + getOrigemAmostra() + "'" +
            ", tipoAmostra='" + getTipoAmostra() + "'" +
            ", identificacao='" + getIdentificacao() + "'" +
            ", dataSoro='" + getDataSoro() + "'" +
            ", material='" + getMaterial() + "'" +
            ", especialidadeId=" + getEspecialidadeId() +
            ", centroAtividadeId=" + getCentroAtividadeId() +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", laboratorioId=" + getLaboratorioId() +
            ", laboratorioNome='" + getLaboratorioNome() + "'" +
            ", controleId=" + getControleId() +
            ", controleCodigo='" + getControleCodigo() + "'" +
            ", cadaverId=" + getCadaverId() +
            ", cadaverNome='" + getCadaverNome() + "'" +
            "}";
    }
}
