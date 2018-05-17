package br.com.basis.madre.cadastros.domain;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RelUsuarioUnidadehospitalar.
 */
@Entity
@Table(name = "rel_usuario_unidadehospitalar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "relusuariounidadehospitalar")
public class RelUsuarioUnidadehospitalar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private UnidadeHospitalar unidadeHospitalar;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public RelUsuarioUnidadehospitalar usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UnidadeHospitalar getUnidadeHospitalar() {
        return unidadeHospitalar;
    }

    public RelUsuarioUnidadehospitalar unidadeHospitalar(UnidadeHospitalar unidadeHospitalar) {
        this.unidadeHospitalar = unidadeHospitalar;
        return this;
    }

    public void setUnidadeHospitalar(UnidadeHospitalar unidadeHospitalar) {
        this.unidadeHospitalar = unidadeHospitalar;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelUsuarioUnidadehospitalar relUsuarioUnidadehospitalar = (RelUsuarioUnidadehospitalar) o;
        if (relUsuarioUnidadehospitalar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relUsuarioUnidadehospitalar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelUsuarioUnidadehospitalar{" +
            "id=" + getId() +
            "}";
    }
}
