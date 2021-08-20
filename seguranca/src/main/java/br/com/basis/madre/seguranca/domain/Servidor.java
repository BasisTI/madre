package br.com.basis.madre.seguranca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.seguranca.domain.enumeration.SituacaoDoServidor;

import br.com.basis.madre.seguranca.domain.enumeration.TipoDeRemuneracao;

/**
 * A Servidor.
 */
@Entity
@Table(name = "servidor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "servidor")
public class Servidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqServidor")
    @SequenceGenerator(name = "seqServidor")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "codigo_starh")
    private String codigoStarh;

    @NotNull
    @Column(name = "inicio_do_vinculo", nullable = false)
    private LocalDate inicioDoVinculo;

    @Column(name = "fim_do_vinculo")
    private LocalDate fimDoVinculo;

    @Column(name = "situacao")
    private Boolean situacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_do_servidor")
    private SituacaoDoServidor situacaoDoServidor;

    @Column(name = "centro_de_atividade_id_lotacao")
    private Integer centroDeAtividadeIdLotacao;

    @Column(name = "centro_de_atividade_id_atuacao")
    private Integer centroDeAtividadeIdAtuacao;

    @Column(name = "ocupacao")
    private String ocupacao;

    @Column(name = "carga_horaria")
    private String cargaHoraria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_remuneracao")
    private TipoDeRemuneracao tipoDeRemuneracao;

    @Column(name = "idade")
    private String idade;

    @Column(name = "tempo_de_contrato")
    private String tempoDeContrato;

    @Column(name = "funcao_do_cracha")
    private String funcaoDoCracha;

    @Column(name = "chefe_do_centro_de_atividade")
    private String chefeDoCentroDeAtividade;

    @OneToOne
    @JoinColumn(unique = true)
    private Vinculo vinculo;

    @OneToOne
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToOne
    @JoinColumn(unique = true)
    private Ramal ramal;

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GrupoFuncional> grupofuncionals = new HashSet<>();

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Graduacao> graduacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "servidors", allowSetters = true)
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Servidor codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMatricula() {
        return matricula;
    }

    public Servidor matricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCodigoStarh() {
        return codigoStarh;
    }

    public Servidor codigoStarh(String codigoStarh) {
        this.codigoStarh = codigoStarh;
        return this;
    }

    public void setCodigoStarh(String codigoStarh) {
        this.codigoStarh = codigoStarh;
    }

    public LocalDate getInicioDoVinculo() {
        return inicioDoVinculo;
    }

    public Servidor inicioDoVinculo(LocalDate inicioDoVinculo) {
        this.inicioDoVinculo = inicioDoVinculo;
        return this;
    }

    public void setInicioDoVinculo(LocalDate inicioDoVinculo) {
        this.inicioDoVinculo = inicioDoVinculo;
    }

    public LocalDate getFimDoVinculo() {
        return fimDoVinculo;
    }

    public Servidor fimDoVinculo(LocalDate fimDoVinculo) {
        this.fimDoVinculo = fimDoVinculo;
        return this;
    }

    public void setFimDoVinculo(LocalDate fimDoVinculo) {
        this.fimDoVinculo = fimDoVinculo;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public Servidor situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public SituacaoDoServidor getSituacaoDoServidor() {
        return situacaoDoServidor;
    }

    public Servidor situacaoDoServidor(SituacaoDoServidor situacaoDoServidor) {
        this.situacaoDoServidor = situacaoDoServidor;
        return this;
    }

    public void setSituacaoDoServidor(SituacaoDoServidor situacaoDoServidor) {
        this.situacaoDoServidor = situacaoDoServidor;
    }

    public Integer getCentroDeAtividadeIdLotacao() {
        return centroDeAtividadeIdLotacao;
    }

    public Servidor centroDeAtividadeIdLotacao(Integer centroDeAtividadeIdLotacao) {
        this.centroDeAtividadeIdLotacao = centroDeAtividadeIdLotacao;
        return this;
    }

    public void setCentroDeAtividadeIdLotacao(Integer centroDeAtividadeIdLotacao) {
        this.centroDeAtividadeIdLotacao = centroDeAtividadeIdLotacao;
    }

    public Integer getCentroDeAtividadeIdAtuacao() {
        return centroDeAtividadeIdAtuacao;
    }

    public Servidor centroDeAtividadeIdAtuacao(Integer centroDeAtividadeIdAtuacao) {
        this.centroDeAtividadeIdAtuacao = centroDeAtividadeIdAtuacao;
        return this;
    }

    public void setCentroDeAtividadeIdAtuacao(Integer centroDeAtividadeIdAtuacao) {
        this.centroDeAtividadeIdAtuacao = centroDeAtividadeIdAtuacao;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public Servidor ocupacao(String ocupacao) {
        this.ocupacao = ocupacao;
        return this;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public Servidor cargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
        return this;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public TipoDeRemuneracao getTipoDeRemuneracao() {
        return tipoDeRemuneracao;
    }

    public Servidor tipoDeRemuneracao(TipoDeRemuneracao tipoDeRemuneracao) {
        this.tipoDeRemuneracao = tipoDeRemuneracao;
        return this;
    }

    public void setTipoDeRemuneracao(TipoDeRemuneracao tipoDeRemuneracao) {
        this.tipoDeRemuneracao = tipoDeRemuneracao;
    }

    public String getIdade() {
        return idade;
    }

    public Servidor idade(String idade) {
        this.idade = idade;
        return this;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTempoDeContrato() {
        return tempoDeContrato;
    }

    public Servidor tempoDeContrato(String tempoDeContrato) {
        this.tempoDeContrato = tempoDeContrato;
        return this;
    }

    public void setTempoDeContrato(String tempoDeContrato) {
        this.tempoDeContrato = tempoDeContrato;
    }

    public String getFuncaoDoCracha() {
        return funcaoDoCracha;
    }

    public Servidor funcaoDoCracha(String funcaoDoCracha) {
        this.funcaoDoCracha = funcaoDoCracha;
        return this;
    }

    public void setFuncaoDoCracha(String funcaoDoCracha) {
        this.funcaoDoCracha = funcaoDoCracha;
    }

    public String getChefeDoCentroDeAtividade() {
        return chefeDoCentroDeAtividade;
    }

    public Servidor chefeDoCentroDeAtividade(String chefeDoCentroDeAtividade) {
        this.chefeDoCentroDeAtividade = chefeDoCentroDeAtividade;
        return this;
    }

    public void setChefeDoCentroDeAtividade(String chefeDoCentroDeAtividade) {
        this.chefeDoCentroDeAtividade = chefeDoCentroDeAtividade;
    }

    public Vinculo getVinculo() {
        return vinculo;
    }

    public Servidor vinculo(Vinculo vinculo) {
        this.vinculo = vinculo;
        return this;
    }

    public void setVinculo(Vinculo vinculo) {
        this.vinculo = vinculo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Servidor pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Ramal getRamal() {
        return ramal;
    }

    public Servidor ramal(Ramal ramal) {
        this.ramal = ramal;
        return this;
    }

    public void setRamal(Ramal ramal) {
        this.ramal = ramal;
    }

    public Set<GrupoFuncional> getGrupofuncionals() {
        return grupofuncionals;
    }

    public Servidor grupofuncionals(Set<GrupoFuncional> grupoFuncionals) {
        this.grupofuncionals = grupoFuncionals;
        return this;
    }

    public Servidor addGrupofuncional(GrupoFuncional grupoFuncional) {
        this.grupofuncionals.add(grupoFuncional);
        grupoFuncional.setServidor(this);
        return this;
    }

    public Servidor removeGrupofuncional(GrupoFuncional grupoFuncional) {
        this.grupofuncionals.remove(grupoFuncional);
        grupoFuncional.setServidor(null);
        return this;
    }

    public void setGrupofuncionals(Set<GrupoFuncional> grupoFuncionals) {
        this.grupofuncionals = grupoFuncionals;
    }

    public Set<Graduacao> getGraduacaos() {
        return graduacaos;
    }

    public Servidor graduacaos(Set<Graduacao> graduacaos) {
        this.graduacaos = graduacaos;
        return this;
    }

    public Servidor addGraduacao(Graduacao graduacao) {
        this.graduacaos.add(graduacao);
        graduacao.setServidor(this);
        return this;
    }

    public Servidor removeGraduacao(Graduacao graduacao) {
        this.graduacaos.remove(graduacao);
        graduacao.setServidor(null);
        return this;
    }

    public void setGraduacaos(Set<Graduacao> graduacaos) {
        this.graduacaos = graduacaos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Servidor usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servidor)) {
            return false;
        }
        return id != null && id.equals(((Servidor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servidor{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", matricula='" + getMatricula() + "'" +
            ", codigoStarh='" + getCodigoStarh() + "'" +
            ", inicioDoVinculo='" + getInicioDoVinculo() + "'" +
            ", fimDoVinculo='" + getFimDoVinculo() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", situacaoDoServidor='" + getSituacaoDoServidor() + "'" +
            ", centroDeAtividadeIdLotacao=" + getCentroDeAtividadeIdLotacao() +
            ", centroDeAtividadeIdAtuacao=" + getCentroDeAtividadeIdAtuacao() +
            ", ocupacao='" + getOcupacao() + "'" +
            ", cargaHoraria='" + getCargaHoraria() + "'" +
            ", tipoDeRemuneracao='" + getTipoDeRemuneracao() + "'" +
            ", idade='" + getIdade() + "'" +
            ", tempoDeContrato='" + getTempoDeContrato() + "'" +
            ", funcaoDoCracha='" + getFuncaoDoCracha() + "'" +
            ", chefeDoCentroDeAtividade='" + getChefeDoCentroDeAtividade() + "'" +
            "}";
    }
}
