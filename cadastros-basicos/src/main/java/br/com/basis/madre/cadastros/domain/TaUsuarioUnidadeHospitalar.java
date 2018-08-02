package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TaUsuarioUnidadeHospitalar.
 */
@Entity
@Table(name = "ta_usuario_unidade_hospitalar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tausuariounidadehospitalar")
public class TaUsuarioUnidadeHospitalar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private Long usuadio_id;

    private Long unidade_hospitalar_id;

    public TaUsuarioUnidadeHospitalar() {
    }

    public TaUsuarioUnidadeHospitalar(Long usuadio_id, Long unidade_hospitalar_id) {
        this.usuadio_id = usuadio_id;
        this.unidade_hospitalar_id = unidade_hospitalar_id;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Long getUsuadio_id() {
        return usuadio_id;
    }

    public void setUsuadio_id(Long usuadio_id) {
        this.usuadio_id = usuadio_id;
    }

    public Long getUnidade_hospitalar_id() {
        return unidade_hospitalar_id;
    }

    public void setUnidade_hospitalar_id(Long unidade_hospitalar_id) {
        this.unidade_hospitalar_id = unidade_hospitalar_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar = (TaUsuarioUnidadeHospitalar) o;
        if (taUsuarioUnidadeHospitalar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taUsuarioUnidadeHospitalar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaUsuarioUnidadeHospitalar{" +
            "id=" + getId() +
            "}";
    }
}
