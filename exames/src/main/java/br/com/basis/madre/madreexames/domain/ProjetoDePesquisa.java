package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A ProjetoDePesquisa.
 */
@Entity
@Table(name = "projeto_de_pesquisa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-projetodepesquisa")
public class ProjetoDePesquisa extends DomainBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProjetoDePesquisa")
    @SequenceGenerator(name = "seqProjetoDePesquisa")
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjetoDePesquisa nome(String nome) {
        this.setNome(nome);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjetoDePesquisa)) {
            return false;
        }
        return id != null && id.equals(((ProjetoDePesquisa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjetoDePesquisa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
