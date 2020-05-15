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
 * A PrescricaoDieta.
 */
@Entity
@Table(name = "prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prescricaodieta")
public class PrescricaoDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @OneToMany(mappedBy = "prescricaoDieta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoDieta> itemPrescricaoDietas = new HashSet<>();

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

    public PrescricaoDieta idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public PrescricaoDieta observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<ItemPrescricaoDieta> getItemPrescricaoDietas() {
        return itemPrescricaoDietas;
    }

    public PrescricaoDieta itemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
        return this;
    }

    public PrescricaoDieta addItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.add(itemPrescricaoDieta);
        itemPrescricaoDieta.setPrescricaoDieta(this);
        return this;
    }

    public PrescricaoDieta removeItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
        this.itemPrescricaoDietas.remove(itemPrescricaoDieta);
        itemPrescricaoDieta.setPrescricaoDieta(null);
        return this;
    }

    public void setItemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescricaoDieta)) {
            return false;
        }
        return id != null && id.equals(((PrescricaoDieta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrescricaoDieta{" +
            "id=" + getId() +
            ", idPaciente=" + getIdPaciente() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
