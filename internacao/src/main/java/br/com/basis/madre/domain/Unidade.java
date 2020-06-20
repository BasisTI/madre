package br.com.basis.madre.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.domain.enumeration.Situacao;

/**
 * A Unidade.
 */
@Entity
@Table(name = "unidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unidade")
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao;

    @Column(name = "controle_de_estoque")
    private Boolean controleDeEstoque;

    @Column(name = "id_almoxarifado")
    private Long idAlmoxarifado;

    @NotNull
    @Column(name = "andar", nullable = false)
    private Integer andar;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "horario_inicio")
    private Instant horarioInicio;

    @Column(name = "horario_fim")
    private Instant horarioFim;

    @Column(name = "local_exame")
    private String localExame;

    @Column(name = "rotina_de_funcionamento")
    private String rotinaDeFuncionamento;

    @Column(name = "anexo_documento")
    private Boolean anexoDocumento;

    @Column(name = "setor")
    private Long setor;

    @NotNull
    @Column(name = "id_centro_de_atividade", nullable = false)
    private Long idCentroDeAtividade;

    @Column(name = "id_chefia")
    private Long idChefia;

    @OneToOne
    @JoinColumn(unique = true)
    private Unidade unidadePai;

    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("unidades")
    private TipoUnidade tipoUnidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Prescricao prescricaoEnfermagem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Prescricao prescricaoMedica;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ala> alas = new HashSet<>();

    @OneToMany(mappedBy = "unidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Clinica> clinicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Unidade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public Unidade sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Unidade situacao(Situacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Boolean isControleDeEstoque() {
        return controleDeEstoque;
    }

    public Unidade controleDeEstoque(Boolean controleDeEstoque) {
        this.controleDeEstoque = controleDeEstoque;
        return this;
    }

    public void setControleDeEstoque(Boolean controleDeEstoque) {
        this.controleDeEstoque = controleDeEstoque;
    }

    public Long getIdAlmoxarifado() {
        return idAlmoxarifado;
    }

    public Unidade idAlmoxarifado(Long idAlmoxarifado) {
        this.idAlmoxarifado = idAlmoxarifado;
        return this;
    }

    public void setIdAlmoxarifado(Long idAlmoxarifado) {
        this.idAlmoxarifado = idAlmoxarifado;
    }

    public Integer getAndar() {
        return andar;
    }

    public Unidade andar(Integer andar) {
        this.andar = andar;
        return this;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public Unidade capacidade(Integer capacidade) {
        this.capacidade = capacidade;
        return this;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Instant getHorarioInicio() {
        return horarioInicio;
    }

    public Unidade horarioInicio(Instant horarioInicio) {
        this.horarioInicio = horarioInicio;
        return this;
    }

    public void setHorarioInicio(Instant horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Instant getHorarioFim() {
        return horarioFim;
    }

    public Unidade horarioFim(Instant horarioFim) {
        this.horarioFim = horarioFim;
        return this;
    }

    public void setHorarioFim(Instant horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getLocalExame() {
        return localExame;
    }

    public Unidade localExame(String localExame) {
        this.localExame = localExame;
        return this;
    }

    public void setLocalExame(String localExame) {
        this.localExame = localExame;
    }

    public String getRotinaDeFuncionamento() {
        return rotinaDeFuncionamento;
    }

    public Unidade rotinaDeFuncionamento(String rotinaDeFuncionamento) {
        this.rotinaDeFuncionamento = rotinaDeFuncionamento;
        return this;
    }

    public void setRotinaDeFuncionamento(String rotinaDeFuncionamento) {
        this.rotinaDeFuncionamento = rotinaDeFuncionamento;
    }

    public Boolean isAnexoDocumento() {
        return anexoDocumento;
    }

    public Unidade anexoDocumento(Boolean anexoDocumento) {
        this.anexoDocumento = anexoDocumento;
        return this;
    }

    public void setAnexoDocumento(Boolean anexoDocumento) {
        this.anexoDocumento = anexoDocumento;
    }

    public Long getSetor() {
        return setor;
    }

    public Unidade setor(Long setor) {
        this.setor = setor;
        return this;
    }

    public void setSetor(Long setor) {
        this.setor = setor;
    }

    public Long getIdCentroDeAtividade() {
        return idCentroDeAtividade;
    }

    public Unidade idCentroDeAtividade(Long idCentroDeAtividade) {
        this.idCentroDeAtividade = idCentroDeAtividade;
        return this;
    }

    public void setIdCentroDeAtividade(Long idCentroDeAtividade) {
        this.idCentroDeAtividade = idCentroDeAtividade;
    }

    public Long getIdChefia() {
        return idChefia;
    }

    public Unidade idChefia(Long idChefia) {
        this.idChefia = idChefia;
        return this;
    }

    public void setIdChefia(Long idChefia) {
        this.idChefia = idChefia;
    }

    public Unidade getUnidadePai() {
        return unidadePai;
    }

    public Unidade unidadePai(Unidade unidade) {
        this.unidadePai = unidade;
        return this;
    }

    public void setUnidadePai(Unidade unidade) {
        this.unidadePai = unidade;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public Unidade caracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }

    public Unidade addCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.add(caracteristica);
        caracteristica.setUnidade(this);
        return this;
    }

    public Unidade removeCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.remove(caracteristica);
        caracteristica.setUnidade(null);
        return this;
    }

    public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public TipoUnidade getTipoUnidade() {
        return tipoUnidade;
    }

    public Unidade tipoUnidade(TipoUnidade tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
        return this;
    }

    public void setTipoUnidade(TipoUnidade tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public Prescricao getPrescricaoEnfermagem() {
        return prescricaoEnfermagem;
    }

    public Unidade prescricaoEnfermagem(Prescricao prescricao) {
        this.prescricaoEnfermagem = prescricao;
        return this;
    }

    public void setPrescricaoEnfermagem(Prescricao prescricao) {
        this.prescricaoEnfermagem = prescricao;
    }

    public Prescricao getPrescricaoMedica() {
        return prescricaoMedica;
    }

    public Unidade prescricaoMedica(Prescricao prescricao) {
        this.prescricaoMedica = prescricao;
        return this;
    }

    public void setPrescricaoMedica(Prescricao prescricao) {
        this.prescricaoMedica = prescricao;
    }

    public Cirurgia getCirurgia() {
        return cirurgia;
    }

    public Unidade cirurgia(Cirurgia cirurgia) {
        this.cirurgia = cirurgia;
        return this;
    }

    public void setCirurgia(Cirurgia cirurgia) {
        this.cirurgia = cirurgia;
    }

    public Set<Ala> getAlas() {
        return alas;
    }

    public Unidade alas(Set<Ala> alas) {
        this.alas = alas;
        return this;
    }

    public Unidade addAla(Ala ala) {
        this.alas.add(ala);
        ala.setUnidade(this);
        return this;
    }

    public Unidade removeAla(Ala ala) {
        this.alas.remove(ala);
        ala.setUnidade(null);
        return this;
    }

    public void setAlas(Set<Ala> alas) {
        this.alas = alas;
    }

    public Set<Clinica> getClinicas() {
        return clinicas;
    }

    public Unidade clinicas(Set<Clinica> clinicas) {
        this.clinicas = clinicas;
        return this;
    }

    public Unidade addClinica(Clinica clinica) {
        this.clinicas.add(clinica);
        clinica.setUnidade(this);
        return this;
    }

    public Unidade removeClinica(Clinica clinica) {
        this.clinicas.remove(clinica);
        clinica.setUnidade(null);
        return this;
    }

    public void setClinicas(Set<Clinica> clinicas) {
        this.clinicas = clinicas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unidade)) {
            return false;
        }
        return id != null && id.equals(((Unidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Unidade{" +
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
            "}";
    }
}
