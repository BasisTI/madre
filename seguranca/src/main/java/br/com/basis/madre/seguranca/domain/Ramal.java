package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ramal.
 */
@Entity
@Table(name = "ramal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-ramal")
public class Ramal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRamal")
    @SequenceGenerator(name = "seqRamal")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "urgencia")
    private Boolean urgencia;

    @OneToMany(mappedBy = "ramal")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Servidor> servidorRamals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Ramal numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isUrgencia() {
        return urgencia;
    }

    public Ramal urgencia(Boolean urgencia) {
        this.urgencia = urgencia;
        return this;
    }

    public void setUrgencia(Boolean urgencia) {
        this.urgencia = urgencia;
    }

    public Set<Servidor> getServidorRamals() {
        return new HashSet<>(servidorRamals);
    }

    public Ramal servidorRamals(Set<Servidor> servidors) {
        this.servidorRamals = new HashSet<>(servidors);
        return this;
    }

    public Ramal addServidorRamal(Servidor servidor) {
        this.servidorRamals.add(servidor);
        servidor.setRamal(this);
        return this;
    }

    public Ramal removeServidorRamal(Servidor servidor) {
        this.servidorRamals.remove(servidor);
        servidor.setRamal(null);
        return this;
    }

    public void setServidorRamals(Set<Servidor> servidors) {
        this.servidorRamals = new HashSet<>(servidors);
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ramal)) {
            return false;
        }
        return id != null && id.equals(((Ramal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ramal{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", urgencia='" + isUrgencia() + "'" +
            "}";
    }
}
