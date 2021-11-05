package br.com.basis.madre.seguranca.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.SituacaoDoServidor;
import br.com.basis.madre.seguranca.domain.enumeration.TipoDeRemuneracao;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Servidor} entity.
 */
@ToString
@EqualsAndHashCode
public class ServidorDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codigo;

    private String matricula;

    private String codigoStarh;

    @NotNull
    private LocalDate inicioDoVinculo;

    private LocalDate fimDoVinculo;

    private Boolean situacao;

    private SituacaoDoServidor situacaoDoServidor;

    private Integer centroDeAtividadeIdLotacao;

    private Integer centroDeAtividadeIdAtuacao;

    private String ocupacao;

    private String cargaHoraria;

    private TipoDeRemuneracao tipoDeRemuneracao;

    private String idade;

    private String tempoDeContrato;

    private String funcaoDoCracha;

    private String chefeDoCentroDeAtividade;

    private Long vinculoId;

    private String vinculoDescricao;

    private Long pessoaId;

    private String pessoaNome;

    private Long usuarioId;

    private String usuarioLogin;

    private Long ramalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCodigoStarh() {
        return codigoStarh;
    }

    public void setCodigoStarh(String codigoStarh) {
        this.codigoStarh = codigoStarh;
    }

    public LocalDate getInicioDoVinculo() {
        return inicioDoVinculo;
    }

    public void setInicioDoVinculo(LocalDate inicioDoVinculo) {
        this.inicioDoVinculo = inicioDoVinculo;
    }

    public LocalDate getFimDoVinculo() {
        return fimDoVinculo;
    }

    public void setFimDoVinculo(LocalDate fimDoVinculo) {
        this.fimDoVinculo = fimDoVinculo;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public SituacaoDoServidor getSituacaoDoServidor() {
        return situacaoDoServidor;
    }

    public void setSituacaoDoServidor(SituacaoDoServidor situacaoDoServidor) {
        this.situacaoDoServidor = situacaoDoServidor;
    }

    public Integer getCentroDeAtividadeIdLotacao() {
        return centroDeAtividadeIdLotacao;
    }

    public void setCentroDeAtividadeIdLotacao(Integer centroDeAtividadeIdLotacao) {
        this.centroDeAtividadeIdLotacao = centroDeAtividadeIdLotacao;
    }

    public Integer getCentroDeAtividadeIdAtuacao() {
        return centroDeAtividadeIdAtuacao;
    }

    public void setCentroDeAtividadeIdAtuacao(Integer centroDeAtividadeIdAtuacao) {
        this.centroDeAtividadeIdAtuacao = centroDeAtividadeIdAtuacao;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public TipoDeRemuneracao getTipoDeRemuneracao() {
        return tipoDeRemuneracao;
    }

    public void setTipoDeRemuneracao(TipoDeRemuneracao tipoDeRemuneracao) {
        this.tipoDeRemuneracao = tipoDeRemuneracao;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTempoDeContrato() {
        return tempoDeContrato;
    }

    public void setTempoDeContrato(String tempoDeContrato) {
        this.tempoDeContrato = tempoDeContrato;
    }

    public String getFuncaoDoCracha() {
        return funcaoDoCracha;
    }

    public void setFuncaoDoCracha(String funcaoDoCracha) {
        this.funcaoDoCracha = funcaoDoCracha;
    }

    public String getChefeDoCentroDeAtividade() {
        return chefeDoCentroDeAtividade;
    }

    public void setChefeDoCentroDeAtividade(String chefeDoCentroDeAtividade) {
        this.chefeDoCentroDeAtividade = chefeDoCentroDeAtividade;
    }

    public Long getVinculoId() {
        return vinculoId;
    }

    public void setVinculoId(Long vinculoId) {
        this.vinculoId = vinculoId;
    }

    public String getVinculoDescricao() {
        return vinculoDescricao;
    }

    public void setVinculoDescricao(String vinculoDescricao) {
        this.vinculoDescricao = vinculoDescricao;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Long getRamalId() {
        return ramalId;
    }

    public void setRamalId(Long ramalId) {
        this.ramalId = ramalId;
    }

}
