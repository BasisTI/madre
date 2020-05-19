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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A PrescricaoDieta.
 */
@Data
@Entity
@Table(name = "prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prescricaodieta")
public class PrescricaoDieta implements Serializable {

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

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "prescricaoDieta", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoDieta> itemPrescricaoDietas = new HashSet<>();


    public PrescricaoDieta idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public PrescricaoDieta observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public PrescricaoDieta itemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
        this.itemPrescricaoDietas = itemPrescricaoDietas;
        return this;
    }
    
	public PrescricaoDieta addItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
		this.itemPrescricaoDietas.add(itemPrescricaoDieta);
		itemPrescricaoDieta.setPrescricaoDieta(this);
		return this;
	}

	public PrescricaoDieta removeItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
		this.itemPrescricaoDietas.remove(itemPrescricaoDieta);
		itemPrescricaoDieta.setPrescricaoDieta(null);
		return this;
	}


 
}
