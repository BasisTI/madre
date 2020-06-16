package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Estorno.
 */
@Data
@Entity
@Table(name = "estorno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "estorno")
public class Estorno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "estornado")
    private Boolean estornado;

    @Column(name = "usuario_que_estornou")
    private Long usuarioQueEstornou;

    @Column(name = "data_de_estorno")
    private LocalDate dataDeEstorno;

    @ManyToOne
    @JsonIgnoreProperties("estornos")
    private DispensacaoMedicamentos dispensacaoMedicamentos;

    @ManyToOne
    @JsonIgnoreProperties("estornos")
    private Motivo motivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Boolean isEstornado() {
        return estornado;
    }

    public Estorno estornado(Boolean estornado) {
        this.estornado = estornado;
        return this;
    }



    public Estorno usuarioQueEstornou(Long usuarioQueEstornou) {
        this.usuarioQueEstornou = usuarioQueEstornou;
        return this;
    }



    public Estorno dataDeEstorno(LocalDate dataDeEstorno) {
        this.dataDeEstorno = dataDeEstorno;
        return this;
    }



    public Estorno dispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
        return this;
    }




    public Estorno motivo(Motivo motivo) {
        this.motivo = motivo;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


}
