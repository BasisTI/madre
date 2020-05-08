package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A DispensacaoMedicamentos.
 */
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

    @ManyToOne
    @JsonIgnoreProperties("dispensacaoMedicamentos")
    private Dispensacao dispensacao;

    @ManyToOne
    @JsonIgnoreProperties("dispensacaoMedicamentos")
    private Medicamento medicamentos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFarmacia() {
        return idFarmacia;
    }

    public DispensacaoMedicamentos idFarmacia(Long idFarmacia) {
        this.idFarmacia = idFarmacia;
        return this;
    }

    public void setIdFarmacia(Long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public Boolean isDispensado() {
        return dispensado;
    }

    public DispensacaoMedicamentos dispensado(Boolean dispensado) {
        this.dispensado = dispensado;
        return this;
    }

    public void setDispensado(Boolean dispensado) {
        this.dispensado = dispensado;
    }

    public Long getUsuarioQueDispensou() {
        return usuarioQueDispensou;
    }

    public DispensacaoMedicamentos usuarioQueDispensou(Long usuarioQueDispensou) {
        this.usuarioQueDispensou = usuarioQueDispensou;
        return this;
    }

    public void setUsuarioQueDispensou(Long usuarioQueDispensou) {
        this.usuarioQueDispensou = usuarioQueDispensou;
    }

    public Dispensacao getDispensacao() {
        return dispensacao;
    }

    public DispensacaoMedicamentos dispensacao(Dispensacao dispensacao) {
        this.dispensacao = dispensacao;
        return this;
    }

    public void setDispensacao(Dispensacao dispensacao) {
        this.dispensacao = dispensacao;
    }

    public Medicamento getMedicamentos() {
        return medicamentos;
    }

    public DispensacaoMedicamentos medicamentos(Medicamento medicamento) {
        this.medicamentos = medicamento;
        return this;
    }

    public void setMedicamentos(Medicamento medicamento) {
        this.medicamentos = medicamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispensacaoMedicamentos)) {
            return false;
        }
        return id != null && id.equals(((DispensacaoMedicamentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DispensacaoMedicamentos{" +
            "id=" + getId() +
            ", idFarmacia=" + getIdFarmacia() +
            ", dispensado='" + isDispensado() + "'" +
            ", usuarioQueDispensou=" + getUsuarioQueDispensou() +
            "}";
    }
}
