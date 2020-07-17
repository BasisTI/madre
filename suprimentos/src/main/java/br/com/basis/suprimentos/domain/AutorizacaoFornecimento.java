package br.com.basis.suprimentos.domain;

import br.com.basis.suprimentos.domain.enumeration.TipoItemAf;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "autorizacao_fornecimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-suprimentos-autorizacaofornecimento")
public class AutorizacaoFornecimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numero;

    @NotNull
    @Size(max = 120)
    @Column(name = "complemento", length = 120, nullable = false)
    private String complemento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false)
    private TipoItemAf tipoItem;

    @ManyToOne
    @JsonIgnoreProperties("autorizacaoFornecimentos")
    private Fornecedor fornecedor;
}
