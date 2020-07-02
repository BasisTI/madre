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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "classificacao_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-classificacaomaterial")
public class ClassificacaoMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqClassificacaoMaterial")
    @SequenceGenerator(name = "seqClassificacaoMaterial")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;
}
