package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "lancamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-lancamento")
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLancamento")
    @SequenceGenerator(name = "seqLancamento")
    private Long id;

    @NotNull
    @Column(name = "lancado_em", nullable = false)
    private ZonedDateTime lancadoEm;

    @NotNull
    @Column(name = "lancado_por", nullable = false)
    private String lancadoPor;

    @ManyToOne
    @JsonIgnoreProperties(value = "lancamentos", allowSetters = true)
    private TipoLancamento tipoLancamento;
}
