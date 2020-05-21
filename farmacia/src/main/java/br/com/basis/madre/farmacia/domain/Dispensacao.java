package br.com.basis.madre.farmacia.domain;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dispensacao.
 */
@Data
@Entity
@Table(name = "dispensacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dispensacao")
public class Dispensacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_prescricao")
    private Long idPrescricao;

    @Column(name = "id_unidade")
    private Long idUnidade;

    @Column(name = "usuario_que_criou")
    private Long usuarioQueCriou;

    @Column(name = "data_de_criacao")
    private LocalDate dataDeCriacao;

    @Column(name = "usuario_que_alterou")
    private Long usuarioQueAlterou;

    @Column(name = "data_de_alteracao")
    private LocalDate dataDeAlteracao;

    @OneToMany(mappedBy = "dispensacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DispensacaoMedicamentos> dispensacaoMedicamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Dispensacao idPrescricao(Long idPrescricao) {
        this.idPrescricao = idPrescricao;
        return this;
    }





    public Dispensacao idUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
        return this;
    }


    public Dispensacao usuarioQueCriou(Long usuarioQueCriou) {
        this.usuarioQueCriou = usuarioQueCriou;
        return this;
    }


    public Dispensacao dataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
        return this;
    }


    public Dispensacao usuarioQueAlterou(Long usuarioQueAlterou) {
        this.usuarioQueAlterou = usuarioQueAlterou;
        return this;
    }


    public Dispensacao dataDeAlteracao(LocalDate dataDeAlteracao) {
        this.dataDeAlteracao = dataDeAlteracao;
        return this;
    }


    public Dispensacao dispensacaoMedicamentos(Set<DispensacaoMedicamentos> dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos = dispensacaoMedicamentos;
        return this;
    }

    public Dispensacao addDispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos.add(dispensacaoMedicamentos);
        dispensacaoMedicamentos.setDispensacao(this);
        return this;
    }

    public Dispensacao removeDispensacaoMedicamentos(DispensacaoMedicamentos dispensacaoMedicamentos) {
        this.dispensacaoMedicamentos.remove(dispensacaoMedicamentos);
        dispensacaoMedicamentos.setDispensacao(null);
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


}
