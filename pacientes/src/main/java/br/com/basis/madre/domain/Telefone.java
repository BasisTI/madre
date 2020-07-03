package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.TipoDoContato;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Telefone.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "telefone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @NotNull
    @Column(name = "ddd", nullable = false)
    private String ddd;


    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Field(type = FieldType.Keyword)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDoContato tipo;

    @Field(type = FieldType.Text)
    @Column(name = "observacao")
    private String observacao;

    public Telefone ddd(String ddd) {
        this.ddd = ddd;
        return this;
    }

    public Telefone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public Telefone tipo(TipoDoContato tipo) {
        this.tipo = tipo;
        return this;
    }

    public Telefone observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

}
