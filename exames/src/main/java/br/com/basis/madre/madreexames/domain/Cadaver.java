package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.madre.madreexames.domain.enumeration.Raca;

import br.com.basis.madre.madreexames.domain.enumeration.GrupoSanguineo;

/**
 * A Cadaver.
 */
@Entity
@Table(name = "cadaver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cadaver")
public class Cadaver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCadaver")
    @SequenceGenerator(name = "seqCadaver")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "raca")
    private Raca raca;

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_sanguineo")
    private GrupoSanguineo grupoSanguineo;

    @NotNull
    @Column(name = "data_remocao", nullable = false)
    private LocalDate dataRemocao;

    @NotNull
    @Column(name = "causa_obito", nullable = false)
    private String causaObito;

    @NotNull
    @Column(name = "realizado_por", nullable = false)
    private String realizadoPor;

    @NotNull
    @Column(name = "lido_por", nullable = false)
    private String lidoPor;

    @NotNull
    @Column(name = "procedencia_id", nullable = false)
    private Integer procedenciaId;

    @NotNull
    @Column(name = "retirada_id", nullable = false)
    private Integer retiradaId;

    @NotNull
    @Column(name = "codigo_convenio_id", nullable = false)
    private Integer codigoConvenioId;

    @NotNull
    @Column(name = "observacao", nullable = false)
    private String observacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Cadaver nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Cadaver dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Raca getRaca() {
        return raca;
    }

    public Cadaver raca(Raca raca) {
        this.raca = raca;
        return this;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public Cadaver grupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
        return this;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public LocalDate getDataRemocao() {
        return dataRemocao;
    }

    public Cadaver dataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
        return this;
    }

    public void setDataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getCausaObito() {
        return causaObito;
    }

    public Cadaver causaObito(String causaObito) {
        this.causaObito = causaObito;
        return this;
    }

    public void setCausaObito(String causaObito) {
        this.causaObito = causaObito;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public Cadaver realizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
        return this;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getLidoPor() {
        return lidoPor;
    }

    public Cadaver lidoPor(String lidoPor) {
        this.lidoPor = lidoPor;
        return this;
    }

    public void setLidoPor(String lidoPor) {
        this.lidoPor = lidoPor;
    }

    public Integer getProcedenciaId() {
        return procedenciaId;
    }

    public Cadaver procedenciaId(Integer procedenciaId) {
        this.procedenciaId = procedenciaId;
        return this;
    }

    public void setProcedenciaId(Integer procedenciaId) {
        this.procedenciaId = procedenciaId;
    }

    public Integer getRetiradaId() {
        return retiradaId;
    }

    public Cadaver retiradaId(Integer retiradaId) {
        this.retiradaId = retiradaId;
        return this;
    }

    public void setRetiradaId(Integer retiradaId) {
        this.retiradaId = retiradaId;
    }

    public Integer getCodigoConvenioId() {
        return codigoConvenioId;
    }

    public Cadaver codigoConvenioId(Integer codigoConvenioId) {
        this.codigoConvenioId = codigoConvenioId;
        return this;
    }

    public void setCodigoConvenioId(Integer codigoConvenioId) {
        this.codigoConvenioId = codigoConvenioId;
    }

    public String getObservacao() {
        return observacao;
    }

    public Cadaver observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cadaver)) {
            return false;
        }
        return id != null && id.equals(((Cadaver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cadaver{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", raca='" + getRaca() + "'" +
            ", grupoSanguineo='" + getGrupoSanguineo() + "'" +
            ", dataRemocao='" + getDataRemocao() + "'" +
            ", causaObito='" + getCausaObito() + "'" +
            ", realizadoPor='" + getRealizadoPor() + "'" +
            ", lidoPor='" + getLidoPor() + "'" +
            ", procedenciaId=" + getProcedenciaId() +
            ", retiradaId=" + getRetiradaId() +
            ", codigoConvenioId=" + getCodigoConvenioId() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
