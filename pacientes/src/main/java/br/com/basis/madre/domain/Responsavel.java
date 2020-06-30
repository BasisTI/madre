package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Responsavel.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-responsavel")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "nome_do_responsavel")
    private String nomeDoResponsavel;


    //    @Field(type = FieldType.Nested)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "responsavel_telefone",
        joinColumns = {@JoinColumn(name = "responsavel_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "telefone_id", referencedColumnName = "id")}
    )
    private Set<Telefone> telefones = new HashSet<>();


    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("responsavels")
    private GrauDeParentesco grauDeParentesco;

    public Responsavel nomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
        return this;
    }


    public Responsavel grauDeParentesco(GrauDeParentesco grauDeParentesco) {
        this.grauDeParentesco = grauDeParentesco;
        return this;
    }

}
