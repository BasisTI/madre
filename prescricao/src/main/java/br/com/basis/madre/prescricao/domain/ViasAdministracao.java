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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A ViasAdministracao.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "vias_administracao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "viasadministracao")
public class ViasAdministracao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

    @Column(name = "sigla")
    private String sigla;

    @OneToMany(mappedBy = "viasAdministracao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();


    public ViasAdministracao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }


    public ViasAdministracao sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }


    public ViasAdministracao itemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos) {
        this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
        return this;
    }

    public ViasAdministracao addItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.add(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setViasAdministracao(this);
        return this;
    }

    public ViasAdministracao removeItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.remove(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setViasAdministracao(null);
        return this;
    }

}
