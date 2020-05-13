package br.com.basis.madre.prescricao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento}
 * entity.
 */
public class ItemPrescricaoMedicamentoDTO implements Serializable {

	private Long id;

	@NotNull
	private Long idMedicamento;

	@NotNull
	private Long idListaMedicamentos;

	@NotNull
	@DecimalMin(value = "0")
	private BigDecimal dose;

	@Min(value = 0)
	private Integer frequencia;

	private Boolean todasVias;

	private Boolean bombaInfusao;

	@DecimalMin(value = "0")
	private BigDecimal velocidadeInfusao;

	@Min(value = 0)
	private Integer tempoInfusao;

	private UnidadeTempo unidadeTempo;

	private LocalDate inicioAdministracao;

	private Boolean condicaoNecessaria;

	@Size(max = 255)
	private String observacaoCondicao;

	private Long viasAdministracaoId;

	private Long diluenteId;

	private Long unidadeInfusaoId;

	private Long unidadeDoseId;

	private Long tipoAprazamentoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMedicamento() {
		return idMedicamento;
	}

	public Long getIdListaMedicamentos() {
		return idListaMedicamentos;
	}

	public void setIdListaMedicamentos(Long idListaMedicamentos) {
		this.idListaMedicamentos = idListaMedicamentos;
	}

	public void setListaMedicamentos(Long idListaMedicamentos) {
		this.idListaMedicamentos = idListaMedicamentos;
	}

	public BigDecimal getDose() {
		return dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Boolean isTodasVias() {
		return todasVias;
	}

	public void setTodasVias(Boolean todasVias) {
		this.todasVias = todasVias;
	}

	public Boolean isBombaInfusao() {
		return bombaInfusao;
	}

	public void setBombaInfusao(Boolean bombaInfusao) {
		this.bombaInfusao = bombaInfusao;
	}

	public BigDecimal getVelocidadeInfusao() {
		return velocidadeInfusao;
	}

	public void setVelocidadeInfusao(BigDecimal velocidadeInfusao) {
		this.velocidadeInfusao = velocidadeInfusao;
	}

	public Integer getTempoInfusao() {
		return tempoInfusao;
	}

	public void setTempoInfusao(Integer tempoInfusao) {
		this.tempoInfusao = tempoInfusao;
	}

	public UnidadeTempo getUnidadeTempo() {
		return unidadeTempo;
	}

	public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public LocalDate getInicioAdministracao() {
		return inicioAdministracao;
	}

	public void setInicioAdministracao(LocalDate inicioAdministracao) {
		this.inicioAdministracao = inicioAdministracao;
	}

	public Boolean isCondicaoNecessaria() {
		return condicaoNecessaria;
	}

	public void setCondicaoNecessaria(Boolean condicaoNecessaria) {
		this.condicaoNecessaria = condicaoNecessaria;
	}

	public String getObservacaoCondicao() {
		return observacaoCondicao;
	}

	public void setObservacaoCondicao(String observacaoCondicao) {
		this.observacaoCondicao = observacaoCondicao;
	}

	public Long getViasAdministracaoId() {
		return viasAdministracaoId;
	}

	public void setViasAdministracaoId(Long viasAdministracaoId) {
		this.viasAdministracaoId = viasAdministracaoId;
	}

	public Long getDiluenteId() {
		return diluenteId;
	}

	public void setDiluenteId(Long diluenteId) {
		this.diluenteId = diluenteId;
	}

	public Long getUnidadeInfusaoId() {
		return unidadeInfusaoId;
	}

	public void setUnidadeInfusaoId(Long unidadeInfusaoId) {
		this.unidadeInfusaoId = unidadeInfusaoId;
	}

	public Long getUnidadeDoseId() {
		return unidadeDoseId;
	}

	public void setUnidadeDoseId(Long unidadeDoseId) {
		this.unidadeDoseId = unidadeDoseId;
	}

	public Long getTipoAprazamentoId() {
		return tipoAprazamentoId;
	}

	public void setTipoAprazamentoId(Long tipoAprazamentoId) {
		this.tipoAprazamentoId = tipoAprazamentoId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO = (ItemPrescricaoMedicamentoDTO) o;
		if (itemPrescricaoMedicamentoDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), itemPrescricaoMedicamentoDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ItemPrescricaoMedicamentoDTO [id=" + id + ", idMedicamento=" + idMedicamento + ", idListaMedicamentos="
				+ idListaMedicamentos + ", dose=" + dose + ", frequencia=" + frequencia + ", todasVias=" + todasVias
				+ ", bombaInfusao=" + bombaInfusao + ", velocidadeInfusao=" + velocidadeInfusao + ", tempoInfusao="
				+ tempoInfusao + ", unidadeTempo=" + unidadeTempo + ", inicioAdministracao=" + inicioAdministracao
				+ ", condicaoNecessaria=" + condicaoNecessaria + ", observacaoCondicao=" + observacaoCondicao
				+ ", viasAdministracaoId=" + viasAdministracaoId + ", diluenteId=" + diluenteId + ", unidadeInfusaoId="
				+ unidadeInfusaoId + ", unidadeDoseId=" + unidadeDoseId + ", tipoAprazamentoId=" + tipoAprazamentoId
				+ "]";
	}

}
