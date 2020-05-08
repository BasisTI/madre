package br.com.basis.madre.farmacia.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dispensacao.
 */
@Entity
@Table(name = "dispensacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dispensacao")
public class Dispensacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_prescricao")
    private Long idPrescricao;

    @Column(name = "id_unidade")
    private Long idUnidade;

    @Column(name = "usuario_que_criou")
    private Long usuarioQueCriou;

    @Column(name = "data_de_criacao")
    private LocalDate dataDeCriacao;

    @Column(name = "usuario_que_alterou")
    private Long usuarioQueAlterou;

    @Column(name = "data_de_alteracao")
    private LocalDate dataDeAlteracao;

    @OneToMany(mappedBy = "dispensacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DispensacaoMedicamentos> dispensacaoMedicamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPrescricao() {
        return idPrescricao;
    }

    public Dispensacao idPrescricao(Long idPrescricao) {
        this.idPrescricao = idPrescricao;
        return this;
    }

    public void setIdPrescricao(Long idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public Dispensacao idUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
        return this;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getUsuarioQueCriou() {
        return usuarioQueCriou;
    }

    public Dispensacao usuarioQueCriou(Long usuarioQueCriou) {
        this.usuarioQueCriou = usuarioQueCriou;
        return this;
    }

    public void setUsuarioQueCriou(Long usuarioQueCriou) {
        this.usuarioQueCriou = usuarioQueCriou;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public Dispensacao dataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
        return this;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public Long getUsuarioQueAlterou() {
        return usuarioQueAlterou;
    }

    public Dispensacao usuarioQueAlterou(Long usuarioQueAlterou) {
        this.usuarioQueAlterou = usuarioQueAlterou;
        return this;
    }

    public void setUsuarioQueAlterou(Long usuarioQueAlterou) {
        this.usuarioQueAlterou = usuarioQueAlterou;
    }

    public LocalDate getDataDeAlteracao() {
        return dataDeAlteracao;
    }

    public Dispensacao dataDeAlteracao(LocalDate dataDeAlteracao) {
        this.dataDeAlteracao = dataDeAlteracao;
        return this;
    }

    public void setDataDeAlteracao(LocalDate dataDeAlteracao) {
        this.dataDeAlteracao = dataDeAlteracao;
    }

    public Set<DispensacaoMedicamentos> getDispensacaoMedicamentos() {
        return dispensacaoMedicamentos;
    }

    public Dispensacao dispensacaoMedicamentos(Set<DispensacaoMedicamentos> dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
        return this;
    }

    public Dispensacao addDispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos.add(dispensacaoMedicamentos);
        dispensacaoMedicamentos.setDispensacao(this);
        return this;
    }

    public Dispensacao removeDispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos.remove(dispensacaoMedicamentos);
        dispensacaoMedicamentos.setDispensacao(null);
        return this;
    }

    public void setDispensacaoMedicamentos(Set<DispensacaoMedicamentos> dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dispensacao)) {
            return false;
        }
        return id != null && id.equals(((Dispensacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dispensacao{" +
            "id=" + getId() +
            ", idPrescricao=" + getIdPrescricao() +
            ", idUnidade=" + getIdUnidade() +
            ", usuarioQueCriou=" + getUsuarioQueCriou() +
            ", dataDeCriacao='" + getDataDeCriacao() + "'" +
            ", usuarioQueAlterou=" + getUsuarioQueAlterou() +
            ", dataDeAlteracao='" + getDataDeAlteracao() + "'" +
            "}";
    }
}
