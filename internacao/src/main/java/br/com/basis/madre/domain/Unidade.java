package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Unidade.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "unidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unidade")
public class Unidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao;

    @Column(name = "controle_de_estoque")
    private Boolean controleDeEstoque;

    @Column(name = "id_almoxarifado")
    private Long idAlmoxarifado;

    @NotNull
    @Column(name = "andar", nullable = false)
    private Integer andar;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "horario_inicio")
    private Instant horarioInicio;

    @Column(name = "horario_fim")
    private Instant horarioFim;

    @Column(name = "local_exame")
    private String localExame;

    @Column(name = "rotina_de_funcionamento")
    private String rotinaDeFuncionamento;

    @Column(name = "anexo_documento")
    private Boolean anexoDocumento;

    @Column(name = "setor")
    private Long setor;

    @NotNull
    @Column(name = "id_centro_de_atividade", nullable = false)
    private Long idCentroDeAtividade;

    @Column(name = "id_chefia")
    private Long idChefia;

    @OneToOne
    @JoinColumn(unique = true)
    private Unidade unidadePai;

    @ManyToOne
    @JsonIgnoreProperties("unidades")
    private Ala ala;

    @ManyToOne
    @JsonIgnoreProperties("unidades")
    private Clinica clinica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("unidades")
    private TipoUnidade tipoUnidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Prescricao prescricaoEnfermagem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Prescricao prescricaoMedica;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unidades")
    private Cirurgia cirurgia;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "unidade_caracteristica",
               joinColumns = @JoinColumn(name = "unidade_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "caracteristica_id", referencedColumnName = "id"))
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    public Unidade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Unidade sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public Unidade situacao(Situacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public Boolean isControleDeEstoque() {
        return controleDeEstoque;
    }

    public Unidade controleDeEstoque(Boolean controleDeEstoque) {
        this.controleDeEstoque = controleDeEstoque;
        return this;
    }

    public Unidade idAlmoxarifado(Long idAlmoxarifado) {
        this.idAlmoxarifado = idAlmoxarifado;
        return this;
    }

    public Unidade andar(Integer andar) {
        this.andar = andar;
        return this;
    }

    public Unidade capacidade(Integer capacidade) {
        this.capacidade = capacidade;
        return this;
    }

    public Unidade horarioInicio(Instant horarioInicio) {
        this.horarioInicio = horarioInicio;
        return this;
    }

    public Unidade horarioFim(Instant horarioFim) {
        this.horarioFim = horarioFim;
        return this;
    }

    public Unidade localExame(String localExame) {
        this.localExame = localExame;
        return this;
    }

    public Unidade rotinaDeFuncionamento(String rotinaDeFuncionamento) {
        this.rotinaDeFuncionamento = rotinaDeFuncionamento;
        return this;
    }


    public Boolean isAnexoDocumento() {
        return anexoDocumento;
    }

    public Unidade anexoDocumento(Boolean anexoDocumento) {
        this.anexoDocumento = anexoDocumento;
        return this;
    }

    public Unidade setor(Long setor) {
        this.setor = setor;
        return this;
    }

    public Unidade idCentroDeAtividade(Long idCentroDeAtividade) {
        this.idCentroDeAtividade = idCentroDeAtividade;
        return this;
    }

    public Unidade idChefia(Long idChefia) {
        this.idChefia = idChefia;
        return this;
    }

    public Unidade unidadePai(Unidade unidade) {
        this.unidadePai = unidade;
        return this;
    }

    public Unidade ala(Ala ala) {
        this.ala = ala;
        return this;
    }

    public Unidade clinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public Unidade tipoUnidade(TipoUnidade tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
        return this;
    }

    public Unidade prescricaoEnfermagem(Prescricao prescricao) {
        this.prescricaoEnfermagem = prescricao;
        return this;
    }

    public Unidade prescricaoMedica(Prescricao prescricao) {
        this.prescricaoMedica = prescricao;
        return this;
    }

    public Unidade cirurgia(Cirurgia cirurgia) {
        this.cirurgia = cirurgia;
        return this;
    }

    public Unidade caracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }

    public Unidade addCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.add(caracteristica);
        caracteristica.getUnidades().add(this);
        return this;
    }

    public Unidade removeCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.remove(caracteristica);
        caracteristica.getUnidades().remove(this);
        return this;
    }


}
