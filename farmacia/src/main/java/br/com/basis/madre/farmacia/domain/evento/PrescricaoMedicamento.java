package br.com.basis.madre.farmacia.domain.evento;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * A PrescricaoMedicamento.
 */
@Data
@Document(indexName = "madre-prescricao-prescricaomedicamento")
public class PrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;


    @Field(type = FieldType.Keyword)
    private Long id;


    private Long idPaciente;


    private String observacao;


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



}
