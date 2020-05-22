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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A PrescricaoProcedimentoEspecial.
 */
@Data
@Entity
@Table(name = "prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prescricaoprocedimentoespecial")
public class PrescricaoProcedimentoEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Identificador do paciente
     */
    @NotNull
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    /**
     * Observação ou comentário para a prescrição de procedimento especial
     */
    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @OneToMany(mappedBy = "prescricaoProcedimentoEspecial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecials = new HashSet<>();

    public PrescricaoProcedimentoEspecial idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public PrescricaoProcedimentoEspecial observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }


    public PrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecials(Set<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecials) {
        this.itemPrescricaoProcedimentoEspecials = itemPrescricaoProcedimentoEspecials;
        return this;
    }

    public PrescricaoProcedimentoEspecial addItemPrescricaoProcedimentoEspecial(ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial) {
        this.itemPrescricaoProcedimentoEspecials.add(itemPrescricaoProcedimentoEspecial);
        itemPrescricaoProcedimentoEspecial.setPrescricaoProcedimentoEspecial(this);
        return this;
    }

    public PrescricaoProcedimentoEspecial removeItemPrescricaoProcedimentoEspecial(ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial) {
        this.itemPrescricaoProcedimentoEspecials.remove(itemPrescricaoProcedimentoEspecial);
        itemPrescricaoProcedimentoEspecial.setPrescricaoProcedimentoEspecial(null);
        return this;
    }

}
