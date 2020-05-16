package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A UnidadeDose.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "unidade_dose")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unidadedose")
public class UnidadeDose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 80)
    @Column(name = "descricao", length = 80, nullable = false)
    private String descricao;

    @NotNull
    @NotEmpty
    @Size(max = 10)
    @Column(name = "sigla", length = 10, nullable = false)
    private String sigla;

    @OneToMany(mappedBy = "unidadeDose")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();


    public UnidadeDose descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }


    public UnidadeDose sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }


    public UnidadeDose itemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos) {
        this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
        return this;
    }

    public UnidadeDose addItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.add(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setUnidadeDose(this);
        return this;
    }

    public UnidadeDose removeItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.remove(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setUnidadeDose(null);
        return this;
    }

}
