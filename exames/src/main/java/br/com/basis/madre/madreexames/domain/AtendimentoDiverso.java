package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AtendimentoDiverso.
 */
@Entity
@Table(name = "atendimento_diverso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "atendimentodiverso")
public class AtendimentoDiverso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAtendimentoDiverso")
    @SequenceGenerator(name = "seqAtendimentoDiverso")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToOne
    @JoinColumn(unique = true)
    private InformacoesComplementares informacoes;

    @OneToOne
    @JoinColumn(unique = true)
    private ProjetoDePesquisa projeto;

    @OneToOne
    @JoinColumn(unique = true)
    private LaboratorioExterno laboratorio;

    @OneToOne
    @JoinColumn(unique = true)
    private ControleQualidade controle;

    @OneToOne
    @JoinColumn(unique = true)
    private Cadaver cadaver;

    @OneToMany(mappedBy = "atendimentoDiverso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InformacoesComplementares> atendimentoDiversos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public AtendimentoDiverso codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public AtendimentoDiverso descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public InformacoesComplementares getInformacoes() {
        return informacoes;
    }

    public AtendimentoDiverso informacoes(InformacoesComplementares informacoesComplementares) {
        this.informacoes = informacoesComplementares;
        return this;
    }

    public void setInformacoes(InformacoesComplementares informacoesComplementares) {
        this.informacoes = informacoesComplementares;
    }

    public ProjetoDePesquisa getProjeto() {
        return projeto;
    }

    public AtendimentoDiverso projeto(ProjetoDePesquisa projetoDePesquisa) {
        this.projeto = projetoDePesquisa;
        return this;
    }

    public void setProjeto(ProjetoDePesquisa projetoDePesquisa) {
        this.projeto = projetoDePesquisa;
    }

    public LaboratorioExterno getLaboratorio() {
        return laboratorio;
    }

    public AtendimentoDiverso laboratorio(LaboratorioExterno laboratorioExterno) {
        this.laboratorio = laboratorioExterno;
        return this;
    }

    public void setLaboratorio(LaboratorioExterno laboratorioExterno) {
        this.laboratorio = laboratorioExterno;
    }

    public ControleQualidade getControle() {
        return controle;
    }

    public AtendimentoDiverso controle(ControleQualidade controleQualidade) {
        this.controle = controleQualidade;
        return this;
    }

    public void setControle(ControleQualidade controleQualidade) {
        this.controle = controleQualidade;
    }

    public Cadaver getCadaver() {
        return cadaver;
    }

    public AtendimentoDiverso cadaver(Cadaver cadaver) {
        this.cadaver = cadaver;
        return this;
    }

    public void setCadaver(Cadaver cadaver) {
        this.cadaver = cadaver;
    }

    public Set<InformacoesComplementares> getAtendimentoDiversos() {
        return atendimentoDiversos;
    }

    public AtendimentoDiverso atendimentoDiversos(Set<InformacoesComplementares> informacoesComplementares) {
        this.atendimentoDiversos = informacoesComplementares;
        return this;
    }

    public AtendimentoDiverso addAtendimentoDiverso(InformacoesComplementares informacoesComplementares) {
        this.atendimentoDiversos.add(informacoesComplementares);
        informacoesComplementares.setAtendimentoDiverso(this);
        return this;
    }

    public AtendimentoDiverso removeAtendimentoDiverso(InformacoesComplementares informacoesComplementares) {
        this.atendimentoDiversos.remove(informacoesComplementares);
        informacoesComplementares.setAtendimentoDiverso(null);
        return this;
    }

    public void setAtendimentoDiversos(Set<InformacoesComplementares> informacoesComplementares) {
        this.atendimentoDiversos = informacoesComplementares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtendimentoDiverso)) {
            return false;
        }
        return id != null && id.equals(((AtendimentoDiverso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtendimentoDiverso{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
