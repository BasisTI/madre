package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "requisicao_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Document(indexName = "madre-suprimentos-requisicaomaterial")
public class RequisicaoMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRequisicaoMaterial")
    @SequenceGenerator(name = "seqRequisicaoMaterial")
    private Long id;

    @ManyToOne
    private Almoxarifado almoxarifado;

    @ManyToOne
    private CentroDeAtividade caRequisitante;

    @ManyToOne
    private CentroDeAtividade caAplicacao;

    @NotNull
    @Column(name = "gerado_em", nullable = false)
    private ZonedDateTime geradoEm;

    @NotNull
    @Column(name = "gerado_por", nullable = false)
    private String geradoPor;

    @Column(name = "confirmado_em")
    private ZonedDateTime confirmadoEm;

    @Column(name = "confirmado_por")
    private String confirmadoPor;

    @OneToMany(mappedBy = "requisicao", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ItemRequisicao> itens = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "requisicaoMaterials", allowSetters = true)
    private SituacaoRequisicao situacao;
}
