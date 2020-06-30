package br.com.basis.suprimentos.domain;

import br.com.basis.suprimentos.domain.enumeration.TipoTempoPorClasse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tempo_por_classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-tempoporclasse")
public class TempoPorClasse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTempoPorClasse tipo;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_a", nullable = false)
    private Integer quantidadeClasseA;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_b", nullable = false)
    private Integer quantidadeClasseB;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_c", nullable = false)
    private Integer quantidadeClasseC;

    @ManyToOne
    @JsonIgnoreProperties("tempoPorClasses")
    private Almoxarifado almoxarifado;
}
