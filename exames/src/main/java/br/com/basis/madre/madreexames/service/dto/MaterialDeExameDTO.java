package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.MaterialDeExame} entity.
 */
public class MaterialDeExameDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean ativo;

    private Boolean npo;

    private Boolean jejum;

    private Boolean exigePreparo;

    private Boolean exigeDieta;

    private Boolean informaNumeroDeColetas;

    private Boolean geraItemDeSolicitacao;

    private Boolean exigeIntervaloDeColeta;

    private Boolean exigeRegiaoAnatomica;

    private Boolean ingestaoDeMedicamento;

    private Boolean dependenteDeExame;

    private Boolean analisadoPelaCII;

    private Boolean interesseDaCOMEDI;

    private Boolean exigeImpressao;

    private Boolean apareceResultado;

    private Boolean contaCelulas;

    private Boolean limiteDeSolicitacao;

    private Boolean formaDeRespiracao;

    private Boolean automatico;

    private Boolean exigeDadosComplementares;

    @NotNull
    private String natureza;

    @NotNull
    private String sumario;

    private Integer tempoJejum;

    private Integer intervaloMinimo;

    private String unidadeDeTempo;

    private Integer validade;

    private Integer agendamentoMinimo;

    private Integer tempoLimiteDaSolicitacao;

    private String unidadeDeTempoDaSolicitacao;

    private Integer numeroDeAmostras;

    private Integer numeroDeAmostrasPadrao;

    private Integer diasLimiteDefault;

    private Integer tempoLimiteDefault;

    private Integer numeroDeAmostrarPorIntervalo;

    private Integer tempoLimiteDeAmostraPorIntervalo;

    private String unidadeLimiteDeTempoDoPeriodo;

    @NotNull
    private Boolean permiteSolicitacaoPosAlta;

    private Integer tempoPermitidoParaSolicitarPosAlta;

    private Integer tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;

    private String cartaDeColeta;

    private Boolean laboratoriaTercerizado;

    private Boolean naoCancelaExamaAposAlta;


    private Long materialId;

    private String materialNome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isNpo() {
        return npo;
    }

    public void setNpo(Boolean npo) {
        this.npo = npo;
    }

    public Boolean isJejum() {
        return jejum;
    }

    public void setJejum(Boolean jejum) {
        this.jejum = jejum;
    }

    public Boolean isExigePreparo() {
        return exigePreparo;
    }

    public void setExigePreparo(Boolean exigePreparo) {
        this.exigePreparo = exigePreparo;
    }

    public Boolean isExigeDieta() {
        return exigeDieta;
    }

    public void setExigeDieta(Boolean exigeDieta) {
        this.exigeDieta = exigeDieta;
    }

    public Boolean isInformaNumeroDeColetas() {
        return informaNumeroDeColetas;
    }

    public void setInformaNumeroDeColetas(Boolean informaNumeroDeColetas) {
        this.informaNumeroDeColetas = informaNumeroDeColetas;
    }

    public Boolean isGeraItemDeSolicitacao() {
        return geraItemDeSolicitacao;
    }

    public void setGeraItemDeSolicitacao(Boolean geraItemDeSolicitacao) {
        this.geraItemDeSolicitacao = geraItemDeSolicitacao;
    }

    public Boolean isExigeIntervaloDeColeta() {
        return exigeIntervaloDeColeta;
    }

    public void setExigeIntervaloDeColeta(Boolean exigeIntervaloDeColeta) {
        this.exigeIntervaloDeColeta = exigeIntervaloDeColeta;
    }

    public Boolean isExigeRegiaoAnatomica() {
        return exigeRegiaoAnatomica;
    }

    public void setExigeRegiaoAnatomica(Boolean exigeRegiaoAnatomica) {
        this.exigeRegiaoAnatomica = exigeRegiaoAnatomica;
    }

    public Boolean isIngestaoDeMedicamento() {
        return ingestaoDeMedicamento;
    }

    public void setIngestaoDeMedicamento(Boolean ingestaoDeMedicamento) {
        this.ingestaoDeMedicamento = ingestaoDeMedicamento;
    }

    public Boolean isDependenteDeExame() {
        return dependenteDeExame;
    }

    public void setDependenteDeExame(Boolean dependenteDeExame) {
        this.dependenteDeExame = dependenteDeExame;
    }

    public Boolean isAnalisadoPelaCII() {
        return analisadoPelaCII;
    }

    public void setAnalisadoPelaCII(Boolean analisadoPelaCII) {
        this.analisadoPelaCII = analisadoPelaCII;
    }

    public Boolean isInteresseDaCOMEDI() {
        return interesseDaCOMEDI;
    }

    public void setInteresseDaCOMEDI(Boolean interesseDaCOMEDI) {
        this.interesseDaCOMEDI = interesseDaCOMEDI;
    }

    public Boolean isExigeImpressao() {
        return exigeImpressao;
    }

    public void setExigeImpressao(Boolean exigeImpressao) {
        this.exigeImpressao = exigeImpressao;
    }

    public Boolean isApareceResultado() {
        return apareceResultado;
    }

    public void setApareceResultado(Boolean apareceResultado) {
        this.apareceResultado = apareceResultado;
    }

    public Boolean isContaCelulas() {
        return contaCelulas;
    }

    public void setContaCelulas(Boolean contaCelulas) {
        this.contaCelulas = contaCelulas;
    }

    public Boolean isLimiteDeSolicitacao() {
        return limiteDeSolicitacao;
    }

    public void setLimiteDeSolicitacao(Boolean limiteDeSolicitacao) {
        this.limiteDeSolicitacao = limiteDeSolicitacao;
    }

    public Boolean isFormaDeRespiracao() {
        return formaDeRespiracao;
    }

    public void setFormaDeRespiracao(Boolean formaDeRespiracao) {
        this.formaDeRespiracao = formaDeRespiracao;
    }

    public Boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public Boolean isExigeDadosComplementares() {
        return exigeDadosComplementares;
    }

    public void setExigeDadosComplementares(Boolean exigeDadosComplementares) {
        this.exigeDadosComplementares = exigeDadosComplementares;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Integer getTempoJejum() {
        return tempoJejum;
    }

    public void setTempoJejum(Integer tempoJejum) {
        this.tempoJejum = tempoJejum;
    }

    public Integer getIntervaloMinimo() {
        return intervaloMinimo;
    }

    public void setIntervaloMinimo(Integer intervaloMinimo) {
        this.intervaloMinimo = intervaloMinimo;
    }

    public String getUnidadeDeTempo() {
        return unidadeDeTempo;
    }

    public void setUnidadeDeTempo(String unidadeDeTempo) {
        this.unidadeDeTempo = unidadeDeTempo;
    }

    public Integer getValidade() {
        return validade;
    }

    public void setValidade(Integer validade) {
        this.validade = validade;
    }

    public Integer getAgendamentoMinimo() {
        return agendamentoMinimo;
    }

    public void setAgendamentoMinimo(Integer agendamentoMinimo) {
        this.agendamentoMinimo = agendamentoMinimo;
    }

    public Integer getTempoLimiteDaSolicitacao() {
        return tempoLimiteDaSolicitacao;
    }

    public void setTempoLimiteDaSolicitacao(Integer tempoLimiteDaSolicitacao) {
        this.tempoLimiteDaSolicitacao = tempoLimiteDaSolicitacao;
    }

    public String getUnidadeDeTempoDaSolicitacao() {
        return unidadeDeTempoDaSolicitacao;
    }

    public void setUnidadeDeTempoDaSolicitacao(String unidadeDeTempoDaSolicitacao) {
        this.unidadeDeTempoDaSolicitacao = unidadeDeTempoDaSolicitacao;
    }

    public Integer getNumeroDeAmostras() {
        return numeroDeAmostras;
    }

    public void setNumeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
    }

    public Integer getNumeroDeAmostrasPadrao() {
        return numeroDeAmostrasPadrao;
    }

    public void setNumeroDeAmostrasPadrao(Integer numeroDeAmostrasPadrao) {
        this.numeroDeAmostrasPadrao = numeroDeAmostrasPadrao;
    }

    public Integer getDiasLimiteDefault() {
        return diasLimiteDefault;
    }

    public void setDiasLimiteDefault(Integer diasLimiteDefault) {
        this.diasLimiteDefault = diasLimiteDefault;
    }

    public Integer getTempoLimiteDefault() {
        return tempoLimiteDefault;
    }

    public void setTempoLimiteDefault(Integer tempoLimiteDefault) {
        this.tempoLimiteDefault = tempoLimiteDefault;
    }

    public Integer getNumeroDeAmostrarPorIntervalo() {
        return numeroDeAmostrarPorIntervalo;
    }

    public void setNumeroDeAmostrarPorIntervalo(Integer numeroDeAmostrarPorIntervalo) {
        this.numeroDeAmostrarPorIntervalo = numeroDeAmostrarPorIntervalo;
    }

    public Integer getTempoLimiteDeAmostraPorIntervalo() {
        return tempoLimiteDeAmostraPorIntervalo;
    }

    public void setTempoLimiteDeAmostraPorIntervalo(Integer tempoLimiteDeAmostraPorIntervalo) {
        this.tempoLimiteDeAmostraPorIntervalo = tempoLimiteDeAmostraPorIntervalo;
    }

    public String getUnidadeLimiteDeTempoDoPeriodo() {
        return unidadeLimiteDeTempoDoPeriodo;
    }

    public void setUnidadeLimiteDeTempoDoPeriodo(String unidadeLimiteDeTempoDoPeriodo) {
        this.unidadeLimiteDeTempoDoPeriodo = unidadeLimiteDeTempoDoPeriodo;
    }

    public Boolean isPermiteSolicitacaoPosAlta() {
        return permiteSolicitacaoPosAlta;
    }

    public void setPermiteSolicitacaoPosAlta(Boolean permiteSolicitacaoPosAlta) {
        this.permiteSolicitacaoPosAlta = permiteSolicitacaoPosAlta;
    }

    public Integer getTempoPermitidoParaSolicitarPosAlta() {
        return tempoPermitidoParaSolicitarPosAlta;
    }

    public void setTempoPermitidoParaSolicitarPosAlta(Integer tempoPermitidoParaSolicitarPosAlta) {
        this.tempoPermitidoParaSolicitarPosAlta = tempoPermitidoParaSolicitarPosAlta;
    }

    public Integer getTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras() {
        return tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;
    }

    public void setTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(Integer tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras) {
        this.tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras = tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras;
    }

    public String getCartaDeColeta() {
        return cartaDeColeta;
    }

    public void setCartaDeColeta(String cartaDeColeta) {
        this.cartaDeColeta = cartaDeColeta;
    }

    public Boolean isLaboratoriaTercerizado() {
        return laboratoriaTercerizado;
    }

    public void setLaboratoriaTercerizado(Boolean laboratoriaTercerizado) {
        this.laboratoriaTercerizado = laboratoriaTercerizado;
    }

    public Boolean isNaoCancelaExamaAposAlta() {
        return naoCancelaExamaAposAlta;
    }

    public void setNaoCancelaExamaAposAlta(Boolean naoCancelaExamaAposAlta) {
        this.naoCancelaExamaAposAlta = naoCancelaExamaAposAlta;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialNome() {
        return materialNome;
    }

    public void setMaterialNome(String materialNome) {
        this.materialNome = materialNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialDeExameDTO)) {
            return false;
        }

        return id != null && id.equals(((MaterialDeExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialDeExameDTO{" +
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
            ", materialId=" + getMaterialId() +
            ", materialNome='" + getMaterialNome() + "'" +
            "}";
    }
}
