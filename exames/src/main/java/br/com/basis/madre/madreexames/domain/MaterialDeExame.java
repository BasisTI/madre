package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MaterialDeExame.
 */
@Entity
@Table(name = "material_de_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "materialdeexame")
public class MaterialDeExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMaterialDeExame")
    @SequenceGenerator(name = "seqMaterialDeExame")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "npo")
    private Boolean npo;

    @Column(name = "jejum")
    private Boolean jejum;

    @Column(name = "exige_preparo")
    private Boolean exigePreparo;

    @Column(name = "exige_dieta")
    private Boolean exigeDieta;

    @Column(name = "informa_numero_de_coletas")
    private Boolean informaNumeroDeColetas;

    @Column(name = "gera_item_de_solicitacao")
    private Boolean geraItemDeSolicitacao;

    @Column(name = "exige_intervalo_de_coleta")
    private Boolean exigeIntervaloDeColeta;

    @Column(name = "exige_regiao_anatomica")
    private Boolean exigeRegiaoAnatomica;

    @Column(name = "ingestao_de_medicamento")
    private Boolean ingestaoDeMedicamento;

    @Column(name = "dependente_de_exame")
    private Boolean dependenteDeExame;

    @Column(name = "analisado_pela_cii")
    private Boolean analisadoPelaCII;

    @Column(name = "interesse_da_comedi")
    private Boolean interesseDaCOMEDI;

    @Column(name = "exige_impressao")
    private Boolean exigeImpressao;

    @Column(name = "aparece_resultado")
    private Boolean apareceResultado;

    @Column(name = "conta_celulas")
    private Boolean contaCelulas;

    @Column(name = "limite_de_solicitacao")
    private Boolean limiteDeSolicitacao;

    @Column(name = "forma_de_respiracao")
    private Boolean formaDeRespiracao;

    @Column(name = "automatico")
    private Boolean automatico;

    @Column(name = "exige_dados_complementares")
    private Boolean exigeDadosComplementares;

    @NotNull
    @Column(name = "natureza", nullable = false)
    private String natureza;

    @NotNull
    @Column(name = "sumario", nullable = false)
    private String sumario;

    @Column(name = "tempo_jejum")
    private Integer tempoJejum;

    @Column(name = "intervalo_minimo")
    private Integer intervaloMinimo;

    @Column(name = "unidade_de_tempo")
    private String unidadeDeTempo;

    @Column(name = "validade")
    private Integer validade;

    @Column(name = "agendamento_minimo")
    private Integer agendamentoMinimo;

    @Column(name = "tempo_limite_da_solicitacao")
    private Integer tempoLimiteDaSolicitacao;

    @Column(name = "unidade_de_tempo_da_solicitacao")
    private String unidadeDeTempoDaSolicitacao;

    @Column(name = "numero_de_amostras")
    private Integer numeroDeAmostras;

    @Column(name = "numero_de_amostras_padrao")
    private Integer numeroDeAmostrasPadrao;

    @Column(name = "dias_limite_default")
    private Integer diasLimiteDefault;

    @Column(name = "tempo_limite_default")
    private Integer tempoLimiteDefault;

    @Column(name = "numero_de_amostrar_por_intervalo")
    private Integer numeroDeAmostrarPorIntervalo;

    @Column(name = "tempo_limite_de_amostra_por_intervalo")
    private Integer tempoLimiteDeAmostraPorIntervalo;

    @Column(name = "unidade_limite_de_tempo_do_periodo")
    private String unidadeLimiteDeTempoDoPeriodo;

    @NotNull
    @Column(name = "permite_solicitacao_pos_alta", nullable = false)
    private Boolean permiteSolicitacaoPosAlta;

    @Column(name = "tempo_permitido_para_solicitar_pos_alta")
    private Integer tempoPermitidoParaSolicitarPosAlta;

    @Column(name = "tempo_permitido_para_solicitar_pos_alta_pelas_areas_executoras")
    private Integer tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;

    @Column(name = "carta_de_coleta")
    private String cartaDeColeta;

    @Column(name = "laboratoria_tercerizado")
    private Boolean laboratoriaTercerizado;

    @Column(name = "nao_cancela_exama_apos_alta")
    private Boolean naoCancelaExamaAposAlta;

    @ManyToOne
    @JsonIgnoreProperties(value = "materialDeExames", allowSetters = true)
    private Material material;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public MaterialDeExame nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public MaterialDeExame ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isNpo() {
        return npo;
    }

    public MaterialDeExame npo(Boolean npo) {
        this.npo = npo;
        return this;
    }

    public void setNpo(Boolean npo) {
        this.npo = npo;
    }

    public Boolean isJejum() {
        return jejum;
    }

    public MaterialDeExame jejum(Boolean jejum) {
        this.jejum = jejum;
        return this;
    }

    public void setJejum(Boolean jejum) {
        this.jejum = jejum;
    }

    public Boolean isExigePreparo() {
        return exigePreparo;
    }

    public MaterialDeExame exigePreparo(Boolean exigePreparo) {
        this.exigePreparo = exigePreparo;
        return this;
    }

    public void setExigePreparo(Boolean exigePreparo) {
        this.exigePreparo = exigePreparo;
    }

    public Boolean isExigeDieta() {
        return exigeDieta;
    }

    public MaterialDeExame exigeDieta(Boolean exigeDieta) {
        this.exigeDieta = exigeDieta;
        return this;
    }

    public void setExigeDieta(Boolean exigeDieta) {
        this.exigeDieta = exigeDieta;
    }

    public Boolean isInformaNumeroDeColetas() {
        return informaNumeroDeColetas;
    }

    public MaterialDeExame informaNumeroDeColetas(Boolean informaNumeroDeColetas) {
        this.informaNumeroDeColetas = informaNumeroDeColetas;
        return this;
    }

    public void setInformaNumeroDeColetas(Boolean informaNumeroDeColetas) {
        this.informaNumeroDeColetas = informaNumeroDeColetas;
    }

    public Boolean isGeraItemDeSolicitacao() {
        return geraItemDeSolicitacao;
    }

    public MaterialDeExame geraItemDeSolicitacao(Boolean geraItemDeSolicitacao) {
        this.geraItemDeSolicitacao = geraItemDeSolicitacao;
        return this;
    }

    public void setGeraItemDeSolicitacao(Boolean geraItemDeSolicitacao) {
        this.geraItemDeSolicitacao = geraItemDeSolicitacao;
    }

    public Boolean isExigeIntervaloDeColeta() {
        return exigeIntervaloDeColeta;
    }

    public MaterialDeExame exigeIntervaloDeColeta(Boolean exigeIntervaloDeColeta) {
        this.exigeIntervaloDeColeta = exigeIntervaloDeColeta;
        return this;
    }

    public void setExigeIntervaloDeColeta(Boolean exigeIntervaloDeColeta) {
        this.exigeIntervaloDeColeta = exigeIntervaloDeColeta;
    }

    public Boolean isExigeRegiaoAnatomica() {
        return exigeRegiaoAnatomica;
    }

    public MaterialDeExame exigeRegiaoAnatomica(Boolean exigeRegiaoAnatomica) {
        this.exigeRegiaoAnatomica = exigeRegiaoAnatomica;
        return this;
    }

    public void setExigeRegiaoAnatomica(Boolean exigeRegiaoAnatomica) {
        this.exigeRegiaoAnatomica = exigeRegiaoAnatomica;
    }

    public Boolean isIngestaoDeMedicamento() {
        return ingestaoDeMedicamento;
    }

    public MaterialDeExame ingestaoDeMedicamento(Boolean ingestaoDeMedicamento) {
        this.ingestaoDeMedicamento = ingestaoDeMedicamento;
        return this;
    }

    public void setIngestaoDeMedicamento(Boolean ingestaoDeMedicamento) {
        this.ingestaoDeMedicamento = ingestaoDeMedicamento;
    }

    public Boolean isDependenteDeExame() {
        return dependenteDeExame;
    }

    public MaterialDeExame dependenteDeExame(Boolean dependenteDeExame) {
        this.dependenteDeExame = dependenteDeExame;
        return this;
    }

    public void setDependenteDeExame(Boolean dependenteDeExame) {
        this.dependenteDeExame = dependenteDeExame;
    }

    public Boolean isAnalisadoPelaCII() {
        return analisadoPelaCII;
    }

    public MaterialDeExame analisadoPelaCII(Boolean analisadoPelaCII) {
        this.analisadoPelaCII = analisadoPelaCII;
        return this;
    }

    public void setAnalisadoPelaCII(Boolean analisadoPelaCII) {
        this.analisadoPelaCII = analisadoPelaCII;
    }

    public Boolean isInteresseDaCOMEDI() {
        return interesseDaCOMEDI;
    }

    public MaterialDeExame interesseDaCOMEDI(Boolean interesseDaCOMEDI) {
        this.interesseDaCOMEDI = interesseDaCOMEDI;
        return this;
    }

    public void setInteresseDaCOMEDI(Boolean interesseDaCOMEDI) {
        this.interesseDaCOMEDI = interesseDaCOMEDI;
    }

    public Boolean isExigeImpressao() {
        return exigeImpressao;
    }

    public MaterialDeExame exigeImpressao(Boolean exigeImpressao) {
        this.exigeImpressao = exigeImpressao;
        return this;
    }

    public void setExigeImpressao(Boolean exigeImpressao) {
        this.exigeImpressao = exigeImpressao;
    }

    public Boolean isApareceResultado() {
        return apareceResultado;
    }

    public MaterialDeExame apareceResultado(Boolean apareceResultado) {
        this.apareceResultado = apareceResultado;
        return this;
    }

    public void setApareceResultado(Boolean apareceResultado) {
        this.apareceResultado = apareceResultado;
    }

    public Boolean isContaCelulas() {
        return contaCelulas;
    }

    public MaterialDeExame contaCelulas(Boolean contaCelulas) {
        this.contaCelulas = contaCelulas;
        return this;
    }

    public void setContaCelulas(Boolean contaCelulas) {
        this.contaCelulas = contaCelulas;
    }

    public Boolean isLimiteDeSolicitacao() {
        return limiteDeSolicitacao;
    }

    public MaterialDeExame limiteDeSolicitacao(Boolean limiteDeSolicitacao) {
        this.limiteDeSolicitacao = limiteDeSolicitacao;
        return this;
    }

    public void setLimiteDeSolicitacao(Boolean limiteDeSolicitacao) {
        this.limiteDeSolicitacao = limiteDeSolicitacao;
    }

    public Boolean isFormaDeRespiracao() {
        return formaDeRespiracao;
    }

    public MaterialDeExame formaDeRespiracao(Boolean formaDeRespiracao) {
        this.formaDeRespiracao = formaDeRespiracao;
        return this;
    }

    public void setFormaDeRespiracao(Boolean formaDeRespiracao) {
        this.formaDeRespiracao = formaDeRespiracao;
    }

    public Boolean isAutomatico() {
        return automatico;
    }

    public MaterialDeExame automatico(Boolean automatico) {
        this.automatico = automatico;
        return this;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public Boolean isExigeDadosComplementares() {
        return exigeDadosComplementares;
    }

    public MaterialDeExame exigeDadosComplementares(Boolean exigeDadosComplementares) {
        this.exigeDadosComplementares = exigeDadosComplementares;
        return this;
    }

    public void setExigeDadosComplementares(Boolean exigeDadosComplementares) {
        this.exigeDadosComplementares = exigeDadosComplementares;
    }

    public String getNatureza() {
        return natureza;
    }

    public MaterialDeExame natureza(String natureza) {
        this.natureza = natureza;
        return this;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getSumario() {
        return sumario;
    }

    public MaterialDeExame sumario(String sumario) {
        this.sumario = sumario;
        return this;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Integer getTempoJejum() {
        return tempoJejum;
    }

    public MaterialDeExame tempoJejum(Integer tempoJejum) {
        this.tempoJejum = tempoJejum;
        return this;
    }

    public void setTempoJejum(Integer tempoJejum) {
        this.tempoJejum = tempoJejum;
    }

    public Integer getIntervaloMinimo() {
        return intervaloMinimo;
    }

    public MaterialDeExame intervaloMinimo(Integer intervaloMinimo) {
        this.intervaloMinimo = intervaloMinimo;
        return this;
    }

    public void setIntervaloMinimo(Integer intervaloMinimo) {
        this.intervaloMinimo = intervaloMinimo;
    }

    public String getUnidadeDeTempo() {
        return unidadeDeTempo;
    }

    public MaterialDeExame unidadeDeTempo(String unidadeDeTempo) {
        this.unidadeDeTempo = unidadeDeTempo;
        return this;
    }

    public void setUnidadeDeTempo(String unidadeDeTempo) {
        this.unidadeDeTempo = unidadeDeTempo;
    }

    public Integer getValidade() {
        return validade;
    }

    public MaterialDeExame validade(Integer validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(Integer validade) {
        this.validade = validade;
    }

    public Integer getAgendamentoMinimo() {
        return agendamentoMinimo;
    }

    public MaterialDeExame agendamentoMinimo(Integer agendamentoMinimo) {
        this.agendamentoMinimo = agendamentoMinimo;
        return this;
    }

    public void setAgendamentoMinimo(Integer agendamentoMinimo) {
        this.agendamentoMinimo = agendamentoMinimo;
    }

    public Integer getTempoLimiteDaSolicitacao() {
        return tempoLimiteDaSolicitacao;
    }

    public MaterialDeExame tempoLimiteDaSolicitacao(Integer tempoLimiteDaSolicitacao) {
        this.tempoLimiteDaSolicitacao = tempoLimiteDaSolicitacao;
        return this;
    }

    public void setTempoLimiteDaSolicitacao(Integer tempoLimiteDaSolicitacao) {
        this.tempoLimiteDaSolicitacao = tempoLimiteDaSolicitacao;
    }

    public String getUnidadeDeTempoDaSolicitacao() {
        return unidadeDeTempoDaSolicitacao;
    }

    public MaterialDeExame unidadeDeTempoDaSolicitacao(String unidadeDeTempoDaSolicitacao) {
        this.unidadeDeTempoDaSolicitacao = unidadeDeTempoDaSolicitacao;
        return this;
    }

    public void setUnidadeDeTempoDaSolicitacao(String unidadeDeTempoDaSolicitacao) {
        this.unidadeDeTempoDaSolicitacao = unidadeDeTempoDaSolicitacao;
    }

    public Integer getNumeroDeAmostras() {
        return numeroDeAmostras;
    }

    public MaterialDeExame numeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
        return this;
    }

    public void setNumeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
    }

    public Integer getNumeroDeAmostrasPadrao() {
        return numeroDeAmostrasPadrao;
    }

    public MaterialDeExame numeroDeAmostrasPadrao(Integer numeroDeAmostrasPadrao) {
        this.numeroDeAmostrasPadrao = numeroDeAmostrasPadrao;
        return this;
    }

    public void setNumeroDeAmostrasPadrao(Integer numeroDeAmostrasPadrao) {
        this.numeroDeAmostrasPadrao = numeroDeAmostrasPadrao;
    }

    public Integer getDiasLimiteDefault() {
        return diasLimiteDefault;
    }

    public MaterialDeExame diasLimiteDefault(Integer diasLimiteDefault) {
        this.diasLimiteDefault = diasLimiteDefault;
        return this;
    }

    public void setDiasLimiteDefault(Integer diasLimiteDefault) {
        this.diasLimiteDefault = diasLimiteDefault;
    }

    public Integer getTempoLimiteDefault() {
        return tempoLimiteDefault;
    }

    public MaterialDeExame tempoLimiteDefault(Integer tempoLimiteDefault) {
        this.tempoLimiteDefault = tempoLimiteDefault;
        return this;
    }

    public void setTempoLimiteDefault(Integer tempoLimiteDefault) {
        this.tempoLimiteDefault = tempoLimiteDefault;
    }

    public Integer getNumeroDeAmostrarPorIntervalo() {
        return numeroDeAmostrarPorIntervalo;
    }

    public MaterialDeExame numeroDeAmostrarPorIntervalo(Integer numeroDeAmostrarPorIntervalo) {
        this.numeroDeAmostrarPorIntervalo = numeroDeAmostrarPorIntervalo;
        return this;
    }

    public void setNumeroDeAmostrarPorIntervalo(Integer numeroDeAmostrarPorIntervalo) {
        this.numeroDeAmostrarPorIntervalo = numeroDeAmostrarPorIntervalo;
    }

    public Integer getTempoLimiteDeAmostraPorIntervalo() {
        return tempoLimiteDeAmostraPorIntervalo;
    }

    public MaterialDeExame tempoLimiteDeAmostraPorIntervalo(Integer tempoLimiteDeAmostraPorIntervalo) {
        this.tempoLimiteDeAmostraPorIntervalo = tempoLimiteDeAmostraPorIntervalo;
        return this;
    }

    public void setTempoLimiteDeAmostraPorIntervalo(Integer tempoLimiteDeAmostraPorIntervalo) {
        this.tempoLimiteDeAmostraPorIntervalo = tempoLimiteDeAmostraPorIntervalo;
    }

    public String getUnidadeLimiteDeTempoDoPeriodo() {
        return unidadeLimiteDeTempoDoPeriodo;
    }

    public MaterialDeExame unidadeLimiteDeTempoDoPeriodo(String unidadeLimiteDeTempoDoPeriodo) {
        this.unidadeLimiteDeTempoDoPeriodo = unidadeLimiteDeTempoDoPeriodo;
        return this;
    }

    public void setUnidadeLimiteDeTempoDoPeriodo(String unidadeLimiteDeTempoDoPeriodo) {
        this.unidadeLimiteDeTempoDoPeriodo = unidadeLimiteDeTempoDoPeriodo;
    }

    public Boolean isPermiteSolicitacaoPosAlta() {
        return permiteSolicitacaoPosAlta;
    }

    public MaterialDeExame permiteSolicitacaoPosAlta(Boolean permiteSolicitacaoPosAlta) {
        this.permiteSolicitacaoPosAlta = permiteSolicitacaoPosAlta;
        return this;
    }

    public void setPermiteSolicitacaoPosAlta(Boolean permiteSolicitacaoPosAlta) {
        this.permiteSolicitacaoPosAlta = permiteSolicitacaoPosAlta;
    }

    public Integer getTempoPermitidoParaSolicitarPosAlta() {
        return tempoPermitidoParaSolicitarPosAlta;
    }

    public MaterialDeExame tempoPermitidoParaSolicitarPosAlta(Integer tempoPermitidoParaSolicitarPosAlta) {
        this.tempoPermitidoParaSolicitarPosAlta = tempoPermitidoParaSolicitarPosAlta;
        return this;
    }

    public void setTempoPermitidoParaSolicitarPosAlta(Integer tempoPermitidoParaSolicitarPosAlta) {
        this.tempoPermitidoParaSolicitarPosAlta = tempoPermitidoParaSolicitarPosAlta;
    }

    public Integer getTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras() {
        return tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;
    }

    public MaterialDeExame tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(Integer tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras) {
        this.tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras = tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;
        return this;
    }

    public void setTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(Integer tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras) {
        this.tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras = tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;
    }

    public String getCartaDeColeta() {
        return cartaDeColeta;
    }

    public MaterialDeExame cartaDeColeta(String cartaDeColeta) {
        this.cartaDeColeta = cartaDeColeta;
        return this;
    }

    public void setCartaDeColeta(String cartaDeColeta) {
        this.cartaDeColeta = cartaDeColeta;
    }

    public Boolean isLaboratoriaTercerizado() {
        return laboratoriaTercerizado;
    }

    public MaterialDeExame laboratoriaTercerizado(Boolean laboratoriaTercerizado) {
        this.laboratoriaTercerizado = laboratoriaTercerizado;
        return this;
    }

    public void setLaboratoriaTercerizado(Boolean laboratoriaTercerizado) {
        this.laboratoriaTercerizado = laboratoriaTercerizado;
    }

    public Boolean isNaoCancelaExamaAposAlta() {
        return naoCancelaExamaAposAlta;
    }

    public MaterialDeExame naoCancelaExamaAposAlta(Boolean naoCancelaExamaAposAlta) {
        this.naoCancelaExamaAposAlta = naoCancelaExamaAposAlta;
        return this;
    }

    public void setNaoCancelaExamaAposAlta(Boolean naoCancelaExamaAposAlta) {
        this.naoCancelaExamaAposAlta = naoCancelaExamaAposAlta;
    }

    public Material getMaterial() {
        return material;
    }

    public MaterialDeExame material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialDeExame)) {
            return false;
        }
        return id != null && id.equals(((MaterialDeExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialDeExame{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", npo='" + isNpo() + "'" +
            ", jejum='" + isJejum() + "'" +
            ", exigePreparo='" + isExigePreparo() + "'" +
            ", exigeDieta='" + isExigeDieta() + "'" +
            ", informaNumeroDeColetas='" + isInformaNumeroDeColetas() + "'" +
            ", geraItemDeSolicitacao='" + isGeraItemDeSolicitacao() + "'" +
            ", exigeIntervaloDeColeta='" + isExigeIntervaloDeColeta() + "'" +
            ", exigeRegiaoAnatomica='" + isExigeRegiaoAnatomica() + "'" +
            ", ingestaoDeMedicamento='" + isIngestaoDeMedicamento() + "'" +
            ", dependenteDeExame='" + isDependenteDeExame() + "'" +
            ", analisadoPelaCII='" + isAnalisadoPelaCII() + "'" +
            ", interesseDaCOMEDI='" + isInteresseDaCOMEDI() + "'" +
            ", exigeImpressao='" + isExigeImpressao() + "'" +
            ", apareceResultado='" + isApareceResultado() + "'" +
            ", contaCelulas='" + isContaCelulas() + "'" +
            ", limiteDeSolicitacao='" + isLimiteDeSolicitacao() + "'" +
            ", formaDeRespiracao='" + isFormaDeRespiracao() + "'" +
            ", automatico='" + isAutomatico() + "'" +
            ", exigeDadosComplementares='" + isExigeDadosComplementares() + "'" +
            ", natureza='" + getNatureza() + "'" +
            ", sumario='" + getSumario() + "'" +
            ", tempoJejum=" + getTempoJejum() +
            ", intervaloMinimo=" + getIntervaloMinimo() +
            ", unidadeDeTempo='" + getUnidadeDeTempo() + "'" +
            ", validade=" + getValidade() +
            ", agendamentoMinimo=" + getAgendamentoMinimo() +
            ", tempoLimiteDaSolicitacao=" + getTempoLimiteDaSolicitacao() +
            ", unidadeDeTempoDaSolicitacao='" + getUnidadeDeTempoDaSolicitacao() + "'" +
            ", numeroDeAmostras=" + getNumeroDeAmostras() +
            ", numeroDeAmostrasPadrao=" + getNumeroDeAmostrasPadrao() +
            ", diasLimiteDefault=" + getDiasLimiteDefault() +
            ", tempoLimiteDefault=" + getTempoLimiteDefault() +
            ", numeroDeAmostrarPorIntervalo=" + getNumeroDeAmostrarPorIntervalo() +
            ", tempoLimiteDeAmostraPorIntervalo=" + getTempoLimiteDeAmostraPorIntervalo() +
            ", unidadeLimiteDeTempoDoPeriodo='" + getUnidadeLimiteDeTempoDoPeriodo() + "'" +
            ", permiteSolicitacaoPosAlta='" + isPermiteSolicitacaoPosAlta() + "'" +
            ", tempoPermitidoParaSolicitarPosAlta=" + getTempoPermitidoParaSolicitarPosAlta() +
            ", tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras=" + getTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras() +
            ", cartaDeColeta='" + getCartaDeColeta() + "'" +
            ", laboratoriaTercerizado='" + isLaboratoriaTercerizado() + "'" +
            ", naoCancelaExamaAposAlta='" + isNaoCancelaExamaAposAlta() + "'" +
            "}";
    }
}
