package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;

public class ItemPrescricaoMedicamentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private Long idMedicamento;

	@NotNull
	private Long idListaMedicamentos;

	private BigDecimal dose;

	private Integer frequencia;

	private Boolean todasVias;

	private Boolean bombaInfusao;

	private BigDecimal velocidadeInfusao;

	private Integer tempoInfusao;

	private UnidadeTempo unidadeTempo;

	private LocalDate inicioAdministracao;

	private Boolean condicaoNecessaria;

	private String observacaoCondicao;

	private Long viasAdministracaoId;

	private Long diluenteId;

	private Long unidadeInfusaoId;

	private Long unidadeDoseId;

	private Long prescricaoMedicamentoId;

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

	public void setIdMedicamento(Long idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public Long getIdListaMedicamentos() {
		return idListaMedicamentos;
	}

	public void setIdListaMedicamentos(Long idListaMedicamentos) {
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

	public Boolean getTodasVias() {
		return todasVias;
	}

	public void setTodasVias(Boolean todasVias) {
		this.todasVias = todasVias;
	}

	public Boolean getBombaInfusao() {
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

	public Boolean getCondicaoNecessaria() {
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

	public Long getPrescricaoMedicamentoId() {
		return prescricaoMedicamentoId;
	}

	public void setPrescricaoMedicamentoId(Long prescricaoMedicamentoId) {
		this.prescricaoMedicamentoId = prescricaoMedicamentoId;
	}

	public Long getTipoAprazamentoId() {
		return tipoAprazamentoId;
	}

	public void setTipoAprazamentoId(Long tipoAprazamentoId) {
		this.tipoAprazamentoId = tipoAprazamentoId;
	}

}
