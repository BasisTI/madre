package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = "estoque")
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-lote")
public class Lote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descricao", length = 120, nullable = false)
    private String descricao;

    @Size(min = 1, max = 15)
    @Column(name = "serie", length = 15)
    private String serie;

    @Min(value = 0L)
    @Column(name = "quantidade_disponivel")
    private Long quantidadeDisponivel;

    @Min(value = 0L)
    @Column(name = "quantidade_bloqueada")
    private Long quantidadeBloqueada;

    @Min(value = 0L)
    @Column(name = "quantidade_problema")
    private Long quantidadeProblema;

    @NotNull
    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @ManyToOne
    @JsonIgnoreProperties("lotes")
    private MarcaComercial marcaComercial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotes")
    private EstoqueAlmoxarifado estoque;
}
