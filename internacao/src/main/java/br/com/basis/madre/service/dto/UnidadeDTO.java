package br.com.basis.madre.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import br.com.basis.madre.domain.enumeration.Situacao;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Unidade} entity.
 */
public class UnidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String sigla;

    @NotNull
    private Situacao situacao;

    private Boolean controleDeEstoque;

    private Long idAlmoxarifado;

    @NotNull
    private Integer andar;

    private Integer capacidade;

    private Instant horarioInicio;

    private Instant horarioFim;

    private String localExame;

    private String rotinaDeFuncionamento;

    private Boolean anexoDocumento;

    private Long setor;

    @NotNull
    private Long idCentroDeAtividade;

    private Long idChefia;


    private Long unidadePaiId;

    private Long alaId;

    private Long clinicaId;

    private Long tipoUnidadeId;

    private Long prescricaoEnfermagemId;

    private Long prescricaoMedicaId;

    private Long cirurgiaId;

    private Set<CaracteristicaDTO> caracteristicas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Boolean isControleDeEstoque() {
        return controleDeEstoque;
    }

    public void setControleDeEstoque(Boolean controleDeEstoque) {
        this.controleDeEstoque = controleDeEstoque;
    }

    public Long getIdAlmoxarifado() {
        return idAlmoxarifado;
    }

    public void setIdAlmoxarifado(Long idAlmoxarifado) {
        this.idAlmoxarifado = idAlmoxarifado;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Instant getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Instant horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Instant getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Instant horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getLocalExame() {
        return localExame;
    }

    public void setLocalExame(String localExame) {
        this.localExame = localExame;
    }

    public String getRotinaDeFuncionamento() {
        return rotinaDeFuncionamento;
    }

    public void setRotinaDeFuncionamento(String rotinaDeFuncionamento) {
        this.rotinaDeFuncionamento = rotinaDeFuncionamento;
    }

    public Boolean isAnexoDocumento() {
        return anexoDocumento;
    }

    public void setAnexoDocumento(Boolean anexoDocumento) {
        this.anexoDocumento = anexoDocumento;
    }

    public Long getSetor() {
        return setor;
    }

    public void setSetor(Long setor) {
        this.setor = setor;
    }

    public Long getIdCentroDeAtividade() {
        return idCentroDeAtividade;
    }

    public void setIdCentroDeAtividade(Long idCentroDeAtividade) {
        this.idCentroDeAtividade = idCentroDeAtividade;
    }

    public Long getIdChefia() {
        return idChefia;
    }

    public void setIdChefia(Long idChefia) {
        this.idChefia = idChefia;
    }

    public Long getUnidadePaiId() {
        return unidadePaiId;
    }

    public void setUnidadePaiId(Long unidadeId) {
        this.unidadePaiId = unidadeId;
    }

    public Long getAlaId() {
        return alaId;
    }

    public void setAlaId(Long alaId) {
        this.alaId = alaId;
    }

    public Long getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(Long clinicaId) {
        this.clinicaId = clinicaId;
    }

    public Long getTipoUnidadeId() {
        return tipoUnidadeId;
    }

    public void setTipoUnidadeId(Long tipoUnidadeId) {
        this.tipoUnidadeId = tipoUnidadeId;
    }

    public Long getPrescricaoEnfermagemId() {
        return prescricaoEnfermagemId;
    }

    public void setPrescricaoEnfermagemId(Long prescricaoId) {
        this.prescricaoEnfermagemId = prescricaoId;
    }

    public Long getPrescricaoMedicaId() {
        return prescricaoMedicaId;
    }

    public void setPrescricaoMedicaId(Long prescricaoId) {
        this.prescricaoMedicaId = prescricaoId;
    }

    public Long getCirurgiaId() {
        return cirurgiaId;
    }

    public void setCirurgiaId(Long cirurgiaId) {
        this.cirurgiaId = cirurgiaId;
    }

    public Set<CaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<CaracteristicaDTO> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeDTO unidadeDTO = (UnidadeDTO) o;
        if (unidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", controleDeEstoque='" + isControleDeEstoque() + "'" +
            ", idAlmoxarifado=" + getIdAlmoxarifado() +
            ", andar=" + getAndar() +
            ", capacidade=" + getCapacidade() +
            ", horarioInicio='" + getHorarioInicio() + "'" +
            ", horarioFim='" + getHorarioFim() + "'" +
            ", localExame='" + getLocalExame() + "'" +
            ", rotinaDeFuncionamento='" + getRotinaDeFuncionamento() + "'" +
            ", anexoDocumento='" + isAnexoDocumento() + "'" +
            ", setor=" + getSetor() +
            ", idCentroDeAtividade=" + getIdCentroDeAtividade() +
            ", idChefia=" + getIdChefia() +
            ", unidadePai=" + getUnidadePaiId() +
            ", ala=" + getAlaId() +
            ", clinica=" + getClinicaId() +
            ", tipoUnidade=" + getTipoUnidadeId() +
            ", prescricaoEnfermagem=" + getPrescricaoEnfermagemId() +
            ", prescricaoMedica=" + getPrescricaoMedicaId() +
            ", cirurgia=" + getCirurgiaId() +
            "}";
    }
}
