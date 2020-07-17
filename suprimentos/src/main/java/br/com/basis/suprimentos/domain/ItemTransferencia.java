package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import javax.validation.constraints.Min;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "transferenciaAlmoxarifado")
@Entity
@Table(name = "item_transferencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-itemtransferencia")
public class ItemTransferencia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Min(0)
    @Column(name = "quantidade_enviada", nullable = false)
    private Integer quantidadeEnviada;

    @ManyToOne
    private Material material;

    @ManyToOne
    @JsonIgnoreProperties("itens")
    private TransferenciaAlmoxarifado transferenciaAlmoxarifado;
}
