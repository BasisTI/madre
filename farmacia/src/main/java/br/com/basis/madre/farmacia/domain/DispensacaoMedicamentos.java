package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A DispensacaoMedicamentos.
 */
@Data
@Entity
@Table(name = "dispensacao_medicamentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dispensacaomedicamentos")
public class DispensacaoMedicamentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_farmacia")
    private Long idFarmacia;

    @Column(name = "dispensado")
    private Boolean dispensado;

    @Column(name = "usuario_que_dispensou")
    private Long usuarioQueDispensou;

    @Column(name = "estornado")
    private Boolean estornado;

    @ManyToOne
    @JsonIgnoreProperties("dispensacaoMedicamentos")
    private Dispensacao dispensacao;

    @ManyToOne
    @JsonIgnoreProperties("dispensacaoMedicamentos")
    private Medicamento medicamentos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public DispensacaoMedicamentos idFarmacia(Long idFarmacia) {
        this.idFarmacia = idFarmacia;
        return this;
    }

    public Boolean isDispensado() {
        return dispensado;
    }

    public DispensacaoMedicamentos dispensado(Boolean dispensado) {
        this.dispensado = dispensado;
        return this;
    }

    public DispensacaoMedicamentos usuarioQueDispensou(Long usuarioQueDispensou) {
        this.usuarioQueDispensou = usuarioQueDispensou;
        return this;
    }


    public Boolean isEstornado() {
        return estornado;
    }

    public DispensacaoMedicamentos estornado(Boolean estornado) {
        this.estornado = estornado;
        return this;
    }

    public DispensacaoMedicamentos dispensacao(Dispensacao dispensacao) {
        this.dispensacao = dispensacao;
        return this;
    }


    public DispensacaoMedicamentos medicamentos(Medicamento medicamento) {
        this.medicamentos = medicamento;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


}
