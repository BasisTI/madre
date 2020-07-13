package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A PrescricaoDiagnostico.
 */
@Data
@Entity
@Table(name = "prescricao_diagnostico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prescricaodiagnostico")
public class PrescricaoDiagnostico extends PrescricaoMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @OneToMany(mappedBy = "prescricaoDiagnostico")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticos = new HashSet<>();


    public PrescricaoDiagnostico itemPrescricaoDiagnosticos(Set<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticos) {
        this.itemPrescricaoDiagnosticos = itemPrescricaoDiagnosticos;
        return this;
    }

    public PrescricaoDiagnostico addItemPrescricaoDiagnostico(ItemPrescricaoDiagnostico itemPrescricaoDiagnostico) {
        this.itemPrescricaoDiagnosticos.add(itemPrescricaoDiagnostico);
        itemPrescricaoDiagnostico.setPrescricaoDiagnostico(this);
        return this;
    }

    public PrescricaoDiagnostico removeItemPrescricaoDiagnostico(ItemPrescricaoDiagnostico itemPrescricaoDiagnostico) {
        this.itemPrescricaoDiagnosticos.remove(itemPrescricaoDiagnostico);
        itemPrescricaoDiagnostico.setPrescricaoDiagnostico(null);
        return this;
    }

}
