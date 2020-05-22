package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PrescricaoProcedimentoEspecial.
 */
@Entity
@Table(name = "prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prescricaoprocedimentoespecial")
public class PrescricaoProcedimentoEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Identificador do paciente
     */
    @NotNull
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    /**
     * Observação ou comentário para a prescrição de procedimento especial
     */
    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @OneToMany(mappedBy = "prescricaoProcedimentoEspecial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecials = new HashSet<>();

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

    public PrescricaoProcedimentoEspecial idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public PrescricaoProcedimentoEspecial observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<ItemPrescricaoProcedimentoEspecial> getItemPrescricaoProcedimentoEspecials() {
        return itemPrescricaoProcedimentoEspecials;
    }

    public PrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecials(Set<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecials) {
        this.itemPrescricaoProcedimentoEspecials = itemPrescricaoProcedimentoEspecials;
        return this;
    }

    public PrescricaoProcedimentoEspecial addItemPrescricaoProcedimentoEspecial(ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial) {
        this.itemPrescricaoProcedimentoEspecials.add(itemPrescricaoProcedimentoEspecial);
        itemPrescricaoProcedimentoEspecial.setPrescricaoProcedimentoEspecial(this);
        return this;
    }

    public PrescricaoProcedimentoEspecial removeItemPrescricaoProcedimentoEspecial(ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial) {
        this.itemPrescricaoProcedimentoEspecials.remove(itemPrescricaoProcedimentoEspecial);
        itemPrescricaoProcedimentoEspecial.setPrescricaoProcedimentoEspecial(null);
        return this;
    }

    public void setItemPrescricaoProcedimentoEspecials(Set<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecials) {
        this.itemPrescricaoProcedimentoEspecials = itemPrescricaoProcedimentoEspecials;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescricaoProcedimentoEspecial)) {
            return false;
        }
        return id != null && id.equals(((PrescricaoProcedimentoEspecial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrescricaoProcedimentoEspecial{" +
            "id=" + getId() +
            ", idPaciente=" + getIdPaciente() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
