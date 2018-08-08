package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    private Long usuarioId;

    private Long unidadeHospitalarId;

    public TaUsuarioUnidadeHospitalar() {
    }

    public TaUsuarioUnidadeHospitalar(Long usuarioId, Long unidadeHospitalarId) {
        this.usuarioId = usuarioId;
        this.unidadeHospitalarId = unidadeHospitalarId;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUnidadeHospitalarId() {
        return unidadeHospitalarId;
    }

    public void setUnidadeHospitalarId(Long unidadeHospitalarId) {
        this.unidadeHospitalarId = unidadeHospitalarId;
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
