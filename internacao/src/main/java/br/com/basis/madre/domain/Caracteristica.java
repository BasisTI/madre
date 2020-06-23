package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Caracteristica.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "caracteristica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "caracteristica")
public class Caracteristica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "caracteristicas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Unidade> unidades = new HashSet<>();

    public Caracteristica nome(String nome) {
        this.nome = nome;
        return this;
    }


    public Caracteristica unidades(Set<Unidade> unidades) {
        this.unidades = unidades;
        return this;
    }

    public Caracteristica addUnidade(Unidade unidade) {
        this.unidades.add(unidade);
        unidade.getCaracteristicas().add(this);
        return this;
    }

    public Caracteristica removeUnidade(Unidade unidade) {
        this.unidades.remove(unidade);
        unidade.getCaracteristicas().remove(this);
        return this;
    }

}
