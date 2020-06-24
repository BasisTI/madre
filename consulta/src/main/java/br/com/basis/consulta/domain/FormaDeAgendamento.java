package br.com.basis.consulta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A FormaDeAgendamento.
 */
@Entity
@Data
@Table(name = "forma_de_agendamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "formadeagendamento")
public class FormaDeAgendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_autorizacao", nullable = false)
    private Long numeroAutorizacao;

    @NotNull
    @Column(name = "autorizacao", nullable = false)
    private String autorizacao;

    @OneToOne(mappedBy = "formaDeAgendamento")
    @JsonIgnore
    private Emergencia emergencia;

    public FormaDeAgendamento numeroAutorizacao(Long numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
        return this;
    }

    public FormaDeAgendamento autorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
        return this;
    }

    public FormaDeAgendamento emergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
        return this;
    }
}
