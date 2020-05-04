package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;

/**
 * A PrescricaoMedicamento.
 */
@Entity
@Table(name = "prescricao_medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prescricaomedicamento")
public class PrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotNull
    @Column(name = "id_medicamento", nullable = false)
    private Long idMedicamento;

    @NotNull
    @Min(value = 0)
    @Column(name = "dose", nullable = false)
    private Integer dose;

    @Min(value = 0)
    @Column(name = "frequencia")
    private Integer frequencia;

    @Column(name = "todas_vias")
    private Boolean todasVias;

    @Min(value = 0)
    @Column(name = "velocida_infusao")
    private Integer velocidaInfusao;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_tempo")
    private UnidadeTempo unidadeTempo;

    @Column(name = "inicio_administracao")
    private LocalDate inicioAdministracao;

    @Column(name = "bomba_infusao")
    private Boolean bombaInfusao;

    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @OneToMany(mappedBy = "prescricaoMedicamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ViasAdministracao> viasAdministracaos = new HashSet<>();

    @OneToMany(mappedBy = "prescricaoMedicamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TipoMedicamento> tipoMedicamentos = new HashSet<>();

    @OneToMany(mappedBy = "prescricaoMedicamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Diluente> diluentes = new HashSet<>();

    @OneToMany(mappedBy = "prescricaoMedicamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeInfusao> unidadeInfusaos = new HashSet<>();

    @OneToMany(mappedBy = "prescricaoMedicamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeDose> unidadeDoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public PrescricaoMedicamento idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public PrescricaoMedicamento idMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
        return this;
    }

    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getDose() {
        return dose;
    }

    public PrescricaoMedicamento dose(Integer dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public PrescricaoMedicamento frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Boolean isTodasVias() {
        return todasVias;
    }

    public PrescricaoMedicamento todasVias(Boolean todasVias) {
        this.todasVias = todasVias;
        return this;
    }

    public void setTodasVias(Boolean todasVias) {
        this.todasVias = todasVias;
    }

    public Integer getVelocidaInfusao() {
        return velocidaInfusao;
    }

    public PrescricaoMedicamento velocidaInfusao(Integer velocidaInfusao) {
        this.velocidaInfusao = velocidaInfusao;
        return this;
    }

    public void setVelocidaInfusao(Integer velocidaInfusao) {
        this.velocidaInfusao = velocidaInfusao;
    }

    public UnidadeTempo getUnidadeTempo() {
        return unidadeTempo;
    }

    public PrescricaoMedicamento unidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
        return this;
    }

    public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
    }

    public LocalDate getInicioAdministracao() {
        return inicioAdministracao;
    }

    public PrescricaoMedicamento inicioAdministracao(LocalDate inicioAdministracao) {
        this.inicioAdministracao = inicioAdministracao;
        return this;
    }

    public void setInicioAdministracao(LocalDate inicioAdministracao) {
        this.inicioAdministracao = inicioAdministracao;
    }

    public Boolean isBombaInfusao() {
        return bombaInfusao;
    }

    public PrescricaoMedicamento bombaInfusao(Boolean bombaInfusao) {
        this.bombaInfusao = bombaInfusao;
        return this;
    }

    public void setBombaInfusao(Boolean bombaInfusao) {
        this.bombaInfusao = bombaInfusao;
    }

    public String getObservacao() {
        return observacao;
    }

    public PrescricaoMedicamento observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<ViasAdministracao> getViasAdministracaos() {
        return viasAdministracaos;
    }

    public PrescricaoMedicamento viasAdministracaos(Set<ViasAdministracao> viasAdministracaos) {
        this.viasAdministracaos = viasAdministracaos;
        return this;
    }

    public PrescricaoMedicamento addViasAdministracao(ViasAdministracao viasAdministracao) {
        this.viasAdministracaos.add(viasAdministracao);
        viasAdministracao.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeViasAdministracao(ViasAdministracao viasAdministracao) {
        this.viasAdministracaos.remove(viasAdministracao);
        viasAdministracao.setPrescricaoMedicamento(null);
        return this;
    }

    public void setViasAdministracaos(Set<ViasAdministracao> viasAdministracaos) {
        this.viasAdministracaos = viasAdministracaos;
    }

    public Set<TipoMedicamento> getTipoMedicamentos() {
        return tipoMedicamentos;
    }

    public PrescricaoMedicamento tipoMedicamentos(Set<TipoMedicamento> tipoMedicamentos) {
        this.tipoMedicamentos = tipoMedicamentos;
        return this;
    }

    public PrescricaoMedicamento addTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamentos.add(tipoMedicamento);
        tipoMedicamento.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamentos.remove(tipoMedicamento);
        tipoMedicamento.setPrescricaoMedicamento(null);
        return this;
    }

    public void setTipoMedicamentos(Set<TipoMedicamento> tipoMedicamentos) {
        this.tipoMedicamentos = tipoMedicamentos;
    }

    public Set<Diluente> getDiluentes() {
        return diluentes;
    }

    public PrescricaoMedicamento diluentes(Set<Diluente> diluentes) {
        this.diluentes = diluentes;
        return this;
    }

    public PrescricaoMedicamento addDiluente(Diluente diluente) {
        this.diluentes.add(diluente);
        diluente.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeDiluente(Diluente diluente) {
        this.diluentes.remove(diluente);
        diluente.setPrescricaoMedicamento(null);
        return this;
    }

    public void setDiluentes(Set<Diluente> diluentes) {
        this.diluentes = diluentes;
    }

    public Set<UnidadeInfusao> getUnidadeInfusaos() {
        return unidadeInfusaos;
    }

    public PrescricaoMedicamento unidadeInfusaos(Set<UnidadeInfusao> unidadeInfusaos) {
        this.unidadeInfusaos = unidadeInfusaos;
        return this;
    }

    public PrescricaoMedicamento addUnidadeInfusao(UnidadeInfusao unidadeInfusao) {
        this.unidadeInfusaos.add(unidadeInfusao);
        unidadeInfusao.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeUnidadeInfusao(UnidadeInfusao unidadeInfusao) {
        this.unidadeInfusaos.remove(unidadeInfusao);
        unidadeInfusao.setPrescricaoMedicamento(null);
        return this;
    }

    public void setUnidadeInfusaos(Set<UnidadeInfusao> unidadeInfusaos) {
        this.unidadeInfusaos = unidadeInfusaos;
    }

    public Set<UnidadeDose> getUnidadeDoses() {
        return unidadeDoses;
    }

    public PrescricaoMedicamento unidadeDoses(Set<UnidadeDose> unidadeDoses) {
        this.unidadeDoses = unidadeDoses;
        return this;
    }

    public PrescricaoMedicamento addUnidadeDose(UnidadeDose unidadeDose) {
        this.unidadeDoses.add(unidadeDose);
        unidadeDose.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeUnidadeDose(UnidadeDose unidadeDose) {
        this.unidadeDoses.remove(unidadeDose);
        unidadeDose.setPrescricaoMedicamento(null);
        return this;
    }

    public void setUnidadeDoses(Set<UnidadeDose> unidadeDoses) {
        this.unidadeDoses = unidadeDoses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescricaoMedicamento)) {
            return false;
        }
        return id != null && id.equals(((PrescricaoMedicamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrescricaoMedicamento{" +
            "id=" + getId() +
            ", idPaciente=" + getIdPaciente() +
            ", idMedicamento=" + getIdMedicamento() +
            ", dose=" + getDose() +
            ", frequencia=" + getFrequencia() +
            ", todasVias='" + isTodasVias() + "'" +
            ", velocidaInfusao=" + getVelocidaInfusao() +
            ", unidadeTempo='" + getUnidadeTempo() + "'" +
            ", inicioAdministracao='" + getInicioAdministracao() + "'" +
            ", bombaInfusao='" + isBombaInfusao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
