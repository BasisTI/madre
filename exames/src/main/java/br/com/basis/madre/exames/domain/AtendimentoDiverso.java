package br.com.basis.madre.exames.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AtendimentoDiverso.
 */
@Entity
@Table(name = "atendimento_diverso")
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
    private InformacoesComplementares atendimentoDiverso;

    @OneToOne
    @JoinColumn(unique = true)
    private ProjetoDePesquisa atendimentoDiverso;

    @OneToOne
    @JoinColumn(unique = true)
    private LaboratorioExterno atendimentoDiverso;

    @OneToOne
    @JoinColumn(unique = true)
    private ControleQualidade atendimentoDiverso;

    @OneToOne
    @JoinColumn(unique = true)
    private Cadaver atendimentoDiverso;

    @OneToMany(mappedBy = "atendimentoDiverso")
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

    public InformacoesComplementares getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public AtendimentoDiverso atendimentoDiverso(InformacoesComplementares informacoesComplementares) {
        this.atendimentoDiverso = informacoesComplementares;
        return this;
    }

    public void setAtendimentoDiverso(InformacoesComplementares informacoesComplementares) {
        this.atendimentoDiverso = informacoesComplementares;
    }

    public ProjetoDePesquisa getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public AtendimentoDiverso atendimentoDiverso(ProjetoDePesquisa projetoDePesquisa) {
        this.atendimentoDiverso = projetoDePesquisa;
        return this;
    }

    public void setAtendimentoDiverso(ProjetoDePesquisa projetoDePesquisa) {
        this.atendimentoDiverso = projetoDePesquisa;
    }

    public LaboratorioExterno getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public AtendimentoDiverso atendimentoDiverso(LaboratorioExterno laboratorioExterno) {
        this.atendimentoDiverso = laboratorioExterno;
        return this;
    }

    public void setAtendimentoDiverso(LaboratorioExterno laboratorioExterno) {
        this.atendimentoDiverso = laboratorioExterno;
    }

    public ControleQualidade getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public AtendimentoDiverso atendimentoDiverso(ControleQualidade controleQualidade) {
        this.atendimentoDiverso = controleQualidade;
        return this;
    }

    public void setAtendimentoDiverso(ControleQualidade controleQualidade) {
        this.atendimentoDiverso = controleQualidade;
    }

    public Cadaver getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public AtendimentoDiverso atendimentoDiverso(Cadaver cadaver) {
        this.atendimentoDiverso = cadaver;
        return this;
    }

    public void setAtendimentoDiverso(Cadaver cadaver) {
        this.atendimentoDiverso = cadaver;
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
