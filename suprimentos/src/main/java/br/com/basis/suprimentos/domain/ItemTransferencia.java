package br.com.basis.suprimentos.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_transferencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-itemtransferencia")
public class ItemTransferencia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemTransferencia")
    @SequenceGenerator(name = "seqItemTransferencia")
    private Long id;

    @NotNull
    @Column(name = "quantidade_envidada", nullable = false)
    private Integer quantidadeEnvidada;

    @ManyToMany(mappedBy = "itens")
    private TransferenciaAlmoxarifado transferenciaAlmoxarifado;

    @ManyToOne
    private Material material;
}
