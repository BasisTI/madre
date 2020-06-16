package br.com.basis.madre.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Unidade} entity.
 */
public class UnidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String sigla;

    @NotNull
    private Boolean situacao;

    private Boolean controleDeEstoque;

    private Long idAlmoxarifado;

    @NotNull
    private Integer andar;

    private Integer capacidade;

    private Instant horarioInicio;

    private Instant horarioFim;

    private String localExame;

    private String rotinaDeFuncionamento;

    private Boolean anexoDocumento;

    private Long setor;

    private Long idAlmorifado;

    @NotNull
    private Long idCentroDeAtividade;

    private Long idChefia;

    private Long idPrescricaoMedica;

    private Long idPrescricaoEnfermagem;

    private Long idCirurgia;


    private Long unidadePaiId;

    private Long tipoUnidadeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Boolean isControleDeEstoque() {
        return controleDeEstoque;
    }

    public void setControleDeEstoque(Boolean controleDeEstoque) {
        this.controleDeEstoque = controleDeEstoque;
    }

    public Long getIdAlmoxarifado() {
        return idAlmoxarifado;
    }

    public void setIdAlmoxarifado(Long idAlmoxarifado) {
        this.idAlmoxarifado = idAlmoxarifado;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Instant getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Instant horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Instant getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Instant horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getLocalExame() {
        return localExame;
    }

    public void setLocalExame(String localExame) {
        this.localExame = localExame;
    }

    public String getRotinaDeFuncionamento() {
        return rotinaDeFuncionamento;
    }

    public void setRotinaDeFuncionamento(String rotinaDeFuncionamento) {
        this.rotinaDeFuncionamento = rotinaDeFuncionamento;
    }

    public Boolean isAnexoDocumento() {
        return anexoDocumento;
    }

    public void setAnexoDocumento(Boolean anexoDocumento) {
        this.anexoDocumento = anexoDocumento;
    }

    public Long getSetor() {
        return setor;
    }

    public void setSetor(Long setor) {
        this.setor = setor;
    }

    public Long getIdAlmorifado() {
        return idAlmorifado;
    }

    public void setIdAlmorifado(Long idAlmorifado) {
        this.idAlmorifado = idAlmorifado;
    }

    public Long getIdCentroDeAtividade() {
        return idCentroDeAtividade;
    }

    public void setIdCentroDeAtividade(Long idCentroDeAtividade) {
        this.idCentroDeAtividade = idCentroDeAtividade;
    }

    public Long getIdChefia() {
        return idChefia;
    }

    public void setIdChefia(Long idChefia) {
        this.idChefia = idChefia;
    }

    public Long getIdPrescricaoMedica() {
        return idPrescricaoMedica;
    }

    public void setIdPrescricaoMedica(Long idPrescricaoMedica) {
        this.idPrescricaoMedica = idPrescricaoMedica;
    }

    public Long getIdPrescricaoEnfermagem() {
        return idPrescricaoEnfermagem;
    }

    public void setIdPrescricaoEnfermagem(Long idPrescricaoEnfermagem) {
        this.idPrescricaoEnfermagem = idPrescricaoEnfermagem;
    }

    public Long getIdCirurgia() {
        return idCirurgia;
    }

    public void setIdCirurgia(Long idCirurgia) {
        this.idCirurgia = idCirurgia;
    }

    public Long getUnidadePaiId() {
        return unidadePaiId;
    }

    public void setUnidadePaiId(Long unidadeId) {
        this.unidadePaiId = unidadeId;
    }

    public Long getTipoUnidadeId() {
        return tipoUnidadeId;
    }

    public void setTipoUnidadeId(Long tipoUnidadeId) {
        this.tipoUnidadeId = tipoUnidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeDTO unidadeDTO = (UnidadeDTO) o;
        if (unidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", controleDeEstoque='" + isControleDeEstoque() + "'" +
            ", idAlmoxarifado=" + getIdAlmoxarifado() +
            ", andar=" + getAndar() +
            ", capacidade=" + getCapacidade() +
            ", horarioInicio='" + getHorarioInicio() + "'" +
            ", horarioFim='" + getHorarioFim() + "'" +
            ", localExame='" + getLocalExame() + "'" +
            ", rotinaDeFuncionamento='" + getRotinaDeFuncionamento() + "'" +
            ", anexoDocumento='" + isAnexoDocumento() + "'" +
            ", setor=" + getSetor() +
            ", idAlmorifado=" + getIdAlmorifado() +
            ", idCentroDeAtividade=" + getIdCentroDeAtividade() +
            ", idChefia=" + getIdChefia() +
            ", idPrescricaoMedica=" + getIdPrescricaoMedica() +
            ", idPrescricaoEnfermagem=" + getIdPrescricaoEnfermagem() +
            ", idCirurgia=" + getIdCirurgia() +
            ", unidadePai=" + getUnidadePaiId() +
            ", tipoUnidade=" + getTipoUnidadeId() +
            "}";
    }
}
