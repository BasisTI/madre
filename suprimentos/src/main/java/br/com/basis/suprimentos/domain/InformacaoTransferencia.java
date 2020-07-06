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
import java.io.Serializable;

@Data
@Entity
@Table(name = "informacao_transferencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-informacaotransferencia")
public class InformacaoTransferencia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInformacaoTransferencia")
    @SequenceGenerator(name = "seqInformacaoTransferencia")
    private Long id;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "efetivada")
    private Boolean efetivada;

    @ManyToOne
    @JsonIgnoreProperties("informacaoTransferencia")
    private ClassificacaoMaterial classificacaoMaterial;

    @ManyToOne
    @JsonIgnoreProperties("informacaoTransferencia")
    private CentroDeAtividade centroDeAtividade;
}
