package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A InformacoesComplementares.
 */
@Entity
@Table(name = "informacoes_complementares")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-informacoescomplementares")
public class InformacoesComplementares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInformacoesComplementares")
    @SequenceGenerator(name = "seqInformacoesComplementares")
    private Long id;

    @NotNull
    @Column(name = "prontuario", nullable = false)
    private Integer prontuario;

    @Column(name = "codigo")
    private Integer codigo;

    @ManyToOne
    @JsonIgnoreProperties(value = "atendimentoDiversos", allowSetters = true)
    private AtendimentoDiverso atendimentoDiverso;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProntuario() {
        return prontuario;
    }

    public InformacoesComplementares prontuario(Integer prontuario) {
        this.prontuario = prontuario;
        return this;
    }

    public void setProntuario(Integer prontuario) {
        this.prontuario = prontuario;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public InformacoesComplementares codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public AtendimentoDiverso getAtendimentoDiverso() {
        return atendimentoDiverso;
    }

    public InformacoesComplementares atendimentoDiverso(AtendimentoDiverso atendimentoDiverso) {
        this.atendimentoDiverso = atendimentoDiverso;
        return this;
    }

    public void setAtendimentoDiverso(AtendimentoDiverso atendimentoDiverso) {
        this.atendimentoDiverso = atendimentoDiverso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformacoesComplementares)) {
            return false;
        }
        return id != null && id.equals(((InformacoesComplementares) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformacoesComplementares{" +
            "id=" + getId() +
            ", prontuario=" + getProntuario() +
            ", codigo=" + getCodigo() +
            "}";
    }
}
