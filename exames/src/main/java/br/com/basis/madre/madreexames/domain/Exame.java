package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A Exame.
 */
@Entity
@Table(name = "exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-exame")
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqExame")
    @SequenceGenerator(name = "seqExame")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_usual")
    private String nomeUsual;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "impressao", nullable = false)
    private Boolean impressao;

    @NotNull
    @Column(name = "consiste_interfaceamento", nullable = false)
    private Boolean consisteInterfaceamento;

    @NotNull
    @Column(name = "anexa_documentos", nullable = false)
    private Boolean anexaDocumentos;

    @OneToMany(mappedBy = "exame", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Sinonimo> sinonimos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "exames", allowSetters = true)
    private MaterialDeExame materialExame;

    @ManyToMany(mappedBy = "exames")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GrupoAgendamentoExame> grupoAgendamentoExames = new HashSet<>();

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

    public Exame nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsual() {
        return nomeUsual;
    }

    public Exame nomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
        return this;
    }

    public void setNomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
    }

    public String getSigla() {
        return sigla;
    }

    public Exame sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Exame ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isImpressao() {
        return impressao;
    }

    public Exame impressao(Boolean impressao) {
        this.impressao = impressao;
        return this;
    }

    public void setImpressao(Boolean impressao) {
        this.impressao = impressao;
    }

    public Boolean isConsisteInterfaceamento() {
        return consisteInterfaceamento;
    }

    public Exame consisteInterfaceamento(Boolean consisteInterfaceamento) {
        this.consisteInterfaceamento = consisteInterfaceamento;
        return this;
    }

    public void setConsisteInterfaceamento(Boolean consisteInterfaceamento) {
        this.consisteInterfaceamento = consisteInterfaceamento;
    }

    public Boolean isAnexaDocumentos() {
        return anexaDocumentos;
    }

    public Exame anexaDocumentos(Boolean anexaDocumentos) {
        this.anexaDocumentos = anexaDocumentos;
        return this;
    }

    public void setAnexaDocumentos(Boolean anexaDocumentos) {
        this.anexaDocumentos = anexaDocumentos;
    }

    public MaterialDeExame getMaterialExame() {
        return materialExame;
    }

    public Exame materialExame(MaterialDeExame materialDeExame) {
        this.materialExame = materialDeExame;
        return this;
    }

    public void setMaterialExame(MaterialDeExame materialDeExame) {
        this.materialExame = materialDeExame;
    }

    public Set<GrupoAgendamentoExame> getGrupoAgendamentoExames() {
        return new HashSet<>(grupoAgendamentoExames);
    }

    public Exame grupoAgendamentoExames(Set<GrupoAgendamentoExame> grupoAgendamentoExames) {
        this.grupoAgendamentoExames = new HashSet<>(grupoAgendamentoExames);
        return this;
    }

    public Exame addGrupoAgendamentoExame(GrupoAgendamentoExame grupoAgendamentoExame) {
        this.grupoAgendamentoExames.add(grupoAgendamentoExame);
        grupoAgendamentoExame.getExames().add(this);
        return this;
    }

    public Exame removeGrupoAgendamentoExame(GrupoAgendamentoExame grupoAgendamentoExame) {
        this.grupoAgendamentoExames.remove(grupoAgendamentoExame);
        grupoAgendamentoExame.getExames().remove(this);
        return this;
    }

    public void setGrupoAgendamentoExames(Set<GrupoAgendamentoExame> grupoAgendamentoExames) {
        grupoAgendamentoExames = new HashSet<>(grupoAgendamentoExames);
        this.grupoAgendamentoExames = Collections.unmodifiableSet(grupoAgendamentoExames);
    }

    public Set<Sinonimo> getSinonimos() {
        return sinonimos;
    }

    public void setSinonimos(Set<Sinonimo> sinonimos) {
        this.sinonimos = sinonimos;
    }

    public Exame addSinonimo(Sinonimo sinonimo) {
        this.sinonimos.add(sinonimo);
        sinonimo.setExame(this);
        return this;
    }

    public Exame removeSinonimo(Sinonimo sinonimo) {
        this.sinonimos.remove(sinonimo);
        sinonimo.setExame(null);
        return this;
    }

    public Exame exame(Set<Sinonimo> sinonimos) {
        this.sinonimos = sinonimos;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exame)) {
            return false;
        }
        return id != null && id.equals(((Exame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exame{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeUsual='" + getNomeUsual() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", impressao='" + isImpressao() + "'" +
            ", consisteInterfaceamento='" + isConsisteInterfaceamento() + "'" +
            ", anexaDocumentos='" + isAnexaDocumentos() + "'" +
            "}";
    }
}
