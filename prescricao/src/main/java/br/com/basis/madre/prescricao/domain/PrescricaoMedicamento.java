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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A PrescricaoMedicamento.
 */
@Getter
@Setter
@ToString
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


    public Set<ItemPrescricaoMedicamento> getItemPrescricaoMedicamentos() {
        return itemPrescricaoMedicamentos;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrescricaoMedicamento other = (PrescricaoMedicamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    

}
