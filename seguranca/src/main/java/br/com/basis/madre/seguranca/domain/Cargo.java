package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cargo.
 */
@Entity
@Table(name = "cargo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-cargo")
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCargo")
    @SequenceGenerator(name = "seqCargo")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "situacao", nullable = false)
    private Boolean situacao;

    @OneToMany(mappedBy = "cargo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OcupacaoDeCargo> ocupacaoDeCargos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Cargo codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cargo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public Cargo situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Set<OcupacaoDeCargo> getOcupacaoDeCargos() {
        return ocupacaoDeCargos;
    }

    public Cargo ocupacaoDeCargos(Set<OcupacaoDeCargo> ocupacaoDeCargos) {
        this.ocupacaoDeCargos = ocupacaoDeCargos;
        return this;
    }

    public Cargo addOcupacaoDeCargo(OcupacaoDeCargo ocupacaoDeCargo) {
        this.ocupacaoDeCargos.add(ocupacaoDeCargo);
        ocupacaoDeCargo.setCargo(this);
        return this;
    }

    public Cargo removeOcupacaoDeCargo(OcupacaoDeCargo ocupacaoDeCargo) {
        this.ocupacaoDeCargos.remove(ocupacaoDeCargo);
        ocupacaoDeCargo.setCargo(null);
        return this;
    }

    public void setOcupacaoDeCargos(Set<OcupacaoDeCargo> ocupacaoDeCargos) {
        this.ocupacaoDeCargos = ocupacaoDeCargos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cargo)) {
            return false;
        }
        return id != null && id.equals(((Cargo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cargo{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
