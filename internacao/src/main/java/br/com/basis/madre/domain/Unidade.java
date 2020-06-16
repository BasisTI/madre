package br.com.basis.madre.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "situacao", nullable = false)
    private Boolean situacao;

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

    @Column(name = "id_almorifado")
    private Long idAlmorifado;

    @NotNull
    @Column(name = "id_centro_de_atividade", nullable = false)
    private Long idCentroDeAtividade;

    @Column(name = "id_chefia")
    private Long idChefia;

    @Column(name = "id_prescricao_medica")
    private Long idPrescricaoMedica;

    @Column(name = "id_prescricao_enfermagem")
    private Long idPrescricaoEnfermagem;

    @Column(name = "id_cirurgia")
    private Long idCirurgia;

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

    public Boolean isSituacao() {
        return situacao;
    }

    public Unidade situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
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

    public Long getIdAlmorifado() {
        return idAlmorifado;
    }

    public Unidade idAlmorifado(Long idAlmorifado) {
        this.idAlmorifado = idAlmorifado;
        return this;
    }

    public void setIdAlmorifado(Long idAlmorifado) {
        this.idAlmorifado = idAlmorifado;
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

    public Long getIdPrescricaoMedica() {
        return idPrescricaoMedica;
    }

    public Unidade idPrescricaoMedica(Long idPrescricaoMedica) {
        this.idPrescricaoMedica = idPrescricaoMedica;
        return this;
    }

    public void setIdPrescricaoMedica(Long idPrescricaoMedica) {
        this.idPrescricaoMedica = idPrescricaoMedica;
    }

    public Long getIdPrescricaoEnfermagem() {
        return idPrescricaoEnfermagem;
    }

    public Unidade idPrescricaoEnfermagem(Long idPrescricaoEnfermagem) {
        this.idPrescricaoEnfermagem = idPrescricaoEnfermagem;
        return this;
    }

    public void setIdPrescricaoEnfermagem(Long idPrescricaoEnfermagem) {
        this.idPrescricaoEnfermagem = idPrescricaoEnfermagem;
    }

    public Long getIdCirurgia() {
        return idCirurgia;
    }

    public Unidade idCirurgia(Long idCirurgia) {
        this.idCirurgia = idCirurgia;
        return this;
    }

    public void setIdCirurgia(Long idCirurgia) {
        this.idCirurgia = idCirurgia;
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
            ", situacao='" + isSituacao() + "'" +
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
            ", idAlmorifado=" + getIdAlmorifado() +
            ", idCentroDeAtividade=" + getIdCentroDeAtividade() +
            ", idChefia=" + getIdChefia() +
            ", idPrescricaoMedica=" + getIdPrescricaoMedica() +
            ", idPrescricaoEnfermagem=" + getIdPrescricaoEnfermagem() +
            ", idCirurgia=" + getIdCirurgia() +
            "}";
    }
}
