package br.com.basis.madre.farmacia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Estorno.
 */
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstornado() {
        return estornado;
    }

    public Estorno estornado(Boolean estornado) {
        this.estornado = estornado;
        return this;
    }

    public void setEstornado(Boolean estornado) {
        this.estornado = estornado;
    }

    public Long getUsuarioQueEstornou() {
        return usuarioQueEstornou;
    }

    public Estorno usuarioQueEstornou(Long usuarioQueEstornou) {
        this.usuarioQueEstornou = usuarioQueEstornou;
        return this;
    }

    public void setUsuarioQueEstornou(Long usuarioQueEstornou) {
        this.usuarioQueEstornou = usuarioQueEstornou;
    }

    public LocalDate getDataDeEstorno() {
        return dataDeEstorno;
    }

    public Estorno dataDeEstorno(LocalDate dataDeEstorno) {
        this.dataDeEstorno = dataDeEstorno;
        return this;
    }

    public void setDataDeEstorno(LocalDate dataDeEstorno) {
        this.dataDeEstorno = dataDeEstorno;
    }

    public DispensacaoMedicamentos getDispensacaoMedicamentos() {
        return dispensacaoMedicamentos;
    }

    public Estorno dispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
        return this;
    }

    public void setDispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public Estorno motivo(Motivo motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estorno)) {
            return false;
        }
        return id != null && id.equals(((Estorno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Estorno{" +
            "id=" + getId() +
            ", estornado='" + isEstornado() + "'" +
            ", usuarioQueEstornou=" + getUsuarioQueEstornou() +
            ", dataDeEstorno='" + getDataDeEstorno() + "'" +
            "}";
    }
}
