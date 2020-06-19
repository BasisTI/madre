package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.Ala;
import br.com.basis.madre.domain.enumeration.Situacao;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Unidade} entity.
 */
public class FormularioUnidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String sigla;

    @NotNull
    private Situacao situacao;

    private Boolean controleDeEstoque;

    private Long idAlmoxarifado;

    private Long ala;

    private Long clinica;

    @NotNull
    private Integer andar;

    private Integer capacidade;

    private Instant horarioInicio;

    private Instant horarioFim;

    private String localExame;

    private String rotinaDeFuncionamento;

    private Boolean anexoDocumento;

    private Long setor;

    @NotNull
    private Long idCentroDeAtividade;

    private Long idChefia;


    private Long unidadePaiId;

    private Long tipoUnidadeId;

    private PrescricaoDTO prescricaoEnfermagem;

    private PrescricaoDTO prescricaoMedica;

    private CirurgiaDTO cirurgia;

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

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Boolean getControleDeEstoque() {
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

    public Long getAla() {
        return ala;
    }

    public void setAla(Long ala) {
        this.ala = ala;
    }

    public Long getClinica() {
        return clinica;
    }

    public void setClinica(Long clinica) {
        this.clinica = clinica;
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

    public Boolean getAnexoDocumento() {
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

    public Long getUnidadePaiId() {
        return unidadePaiId;
    }

    public void setUnidadePaiId(Long unidadePaiId) {
        this.unidadePaiId = unidadePaiId;
    }

    public Long getTipoUnidadeId() {
        return tipoUnidadeId;
    }

    public void setTipoUnidadeId(Long tipoUnidadeId) {
        this.tipoUnidadeId = tipoUnidadeId;
    }

    public PrescricaoDTO getPrescricaoEnfermagem() {
        return prescricaoEnfermagem;
    }

    public void setPrescricaoEnfermagem(PrescricaoDTO prescricaoEnfermagem) {
        this.prescricaoEnfermagem = prescricaoEnfermagem;
    }

    public PrescricaoDTO getPrescricaoMedica() {
        return prescricaoMedica;
    }

    public void setPrescricaoMedica(PrescricaoDTO prescricaoMedica) {
        this.prescricaoMedica = prescricaoMedica;
    }

    public CirurgiaDTO getCirurgia() {
        return cirurgia;
    }

    public void setCirurgia(CirurgiaDTO cirurgia) {
        this.cirurgia = cirurgia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormularioUnidadeDTO unidadeDTO = (FormularioUnidadeDTO) o;
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
        return "FormularioUnidadeDTO{" +
            "id=" + id +
            ", descricao='" + descricao + '\'' +
            ", sigla='" + sigla + '\'' +
            ", situacao=" + situacao +
            ", controleDeEstoque=" + controleDeEstoque +
            ", idAlmoxarifado=" + idAlmoxarifado +
            ", ala=" + ala +
            ", clinica=" + clinica +
            ", andar=" + andar +
            ", capacidade=" + capacidade +
            ", horarioInicio=" + horarioInicio +
            ", horarioFim=" + horarioFim +
            ", localExame='" + localExame + '\'' +
            ", rotinaDeFuncionamento='" + rotinaDeFuncionamento + '\'' +
            ", anexoDocumento=" + anexoDocumento +
            ", setor=" + setor +
            ", idCentroDeAtividade=" + idCentroDeAtividade +
            ", idChefia=" + idChefia +
            ", unidadePaiId=" + unidadePaiId +
            ", tipoUnidadeId=" + tipoUnidadeId +
            ", prescricaoEnfermagem=" + prescricaoEnfermagem +
            ", prescricaoMedica=" + prescricaoMedica +
            ", cirurgia=" + cirurgia +
            '}';
    }
}
