package br.com.basis.madre.prescricao.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Diluente.
 */
@Data
@Entity
@Table(name = "diluente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "diluente")
public class Diluente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotEmpty
    @NotNull
    @Size(max = 100)
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "diluente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();


    public Diluente descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }


    public Diluente itemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos) {
        this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
        return this;
    }

    public Diluente addItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.add(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setDiluente(this);
        return this;
    }

    public Diluente removeItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.remove(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setDiluente(null);
        return this;
    }

}
