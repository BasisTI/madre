package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A PrescricaoDiagnostico.
 */
@Data
@Entity
@Table(name = "prescricao_diagnostico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaomedica", type = "prescricaomedica")
public class PrescricaoDiagnostico extends PrescricaoMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "prescricaoDiagnostico", cascade = CascadeType.ALL)
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
