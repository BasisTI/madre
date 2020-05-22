package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A OrteseProtese.
 */
@Entity
@Table(name = "ortese_protese")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "orteseprotese")
public class OrteseProtese implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * descrição de procedimento especial ortese ou prótese
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "decricao", length = 100, nullable = false)
    private String decricao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecricao() {
        return decricao;
    }

    public OrteseProtese decricao(String decricao) {
        this.decricao = decricao;
        return this;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrteseProtese)) {
            return false;
        }
        return id != null && id.equals(((OrteseProtese) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrteseProtese{" +
            "id=" + getId() +
            ", decricao='" + getDecricao() + "'" +
            "}";
    }
}
