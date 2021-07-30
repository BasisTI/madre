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
 * A OcupacaoDeCargo.
 */
@Entity
@Table(name = "ocupacao_de_cargo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ocupacaodecargo")
public class OcupacaoDeCargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqOcupacaoDeCargo")
    @SequenceGenerator(name = "seqOcupacaoDeCargo")
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

    @Column(name = "informar_cbo")
    private Boolean informarCbo;

    @Column(name = "informar_cns")
    private Boolean informarCns;

    @OneToMany(mappedBy = "ocupacaoDeCargo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Cargo> cargos = new HashSet<>();

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

    public OcupacaoDeCargo codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public OcupacaoDeCargo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public OcupacaoDeCargo situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Boolean isInformarCbo() {
        return informarCbo;
    }

    public OcupacaoDeCargo informarCbo(Boolean informarCbo) {
        this.informarCbo = informarCbo;
        return this;
    }

    public void setInformarCbo(Boolean informarCbo) {
        this.informarCbo = informarCbo;
    }

    public Boolean isInformarCns() {
        return informarCns;
    }

    public OcupacaoDeCargo informarCns(Boolean informarCns) {
        this.informarCns = informarCns;
        return this;
    }

    public void setInformarCns(Boolean informarCns) {
        this.informarCns = informarCns;
    }

    public Set<Cargo> getCargos() {
        return cargos;
    }

    public OcupacaoDeCargo cargos(Set<Cargo> cargos) {
        this.cargos = cargos;
        return this;
    }

    public OcupacaoDeCargo addCargo(Cargo cargo) {
        this.cargos.add(cargo);
        cargo.setOcupacaoDeCargo(this);
        return this;
    }

    public OcupacaoDeCargo removeCargo(Cargo cargo) {
        this.cargos.remove(cargo);
        cargo.setOcupacaoDeCargo(null);
        return this;
    }

    public void setCargos(Set<Cargo> cargos) {
        this.cargos = cargos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcupacaoDeCargo)) {
            return false;
        }
        return id != null && id.equals(((OcupacaoDeCargo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcupacaoDeCargo{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", informarCbo='" + isInformarCbo() + "'" +
            ", informarCns='" + isInformarCns() + "'" +
            "}";
    }
}
