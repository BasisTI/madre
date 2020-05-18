package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A PrescricaoMedicamento.
 */
@Data
@Entity
@Table(name = "prescricao_medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prescricaomedicamento")

public class PrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @OneToMany(mappedBy = "prescricaoMedicamento", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();


    public PrescricaoMedicamento idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public PrescricaoMedicamento observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }



    public PrescricaoMedicamento itemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos) {
        this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
        return this;
    }

    public PrescricaoMedicamento addItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.add(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setPrescricaoMedicamento(this);
        return this;
    }

    public PrescricaoMedicamento removeItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
        this.itemPrescricaoMedicamentos.remove(itemPrescricaoMedicamento);
        itemPrescricaoMedicamento.setPrescricaoMedicamento(null);
        return this;
    }

    
    

}
