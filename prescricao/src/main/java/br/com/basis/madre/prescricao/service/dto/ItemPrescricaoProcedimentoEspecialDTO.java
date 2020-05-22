package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial} entity.
 */
public class ItemPrescricaoProcedimentoEspecialDTO implements Serializable {

    private Long id;

    /**
     * Tipo do procedimento especial
     */
    @ApiModelProperty(value = "Tipo do procedimento especial")
    private TipoProcedimentoEspecial tipoProcedimento;

    /**
     * Quanditade da Ortese ou prótese, valor deve ser um inteiro
     */
    @Min(value = 0)
    @ApiModelProperty(value = "Quanditade da Ortese ou prótese, valor deve ser um inteiro")
    private Integer quantidadeOrteseProtese;

    /**
     * Informações complementares para o procedimento
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Informações complementares para o procedimento")
    private String informacoes;

    /**
     * Justificativa para o procedimento especial
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Justificativa para o procedimento especial")
    private String justificativa;

    /**
     * Duração do procedimento solicitado
     */
    @Min(value = 0)
    @ApiModelProperty(value = "Duração do procedimento solicitado")
    private Integer duracaoSolicitada;


    private Long especiaisDiversosId;

    private Long cirurgiasLeitoId;

    private Long orteseProteseId;

    private Long prescricaoProcedimentoEspecialId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProcedimentoEspecial getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(TipoProcedimentoEspecial tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public Integer getQuantidadeOrteseProtese() {
        return quantidadeOrteseProtese;
    }

    public void setQuantidadeOrteseProtese(Integer quantidadeOrteseProtese) {
        this.quantidadeOrteseProtese = quantidadeOrteseProtese;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getDuracaoSolicitada() {
        return duracaoSolicitada;
    }

    public void setDuracaoSolicitada(Integer duracaoSolicitada) {
        this.duracaoSolicitada = duracaoSolicitada;
    }

    public Long getEspeciaisDiversosId() {
        return especiaisDiversosId;
    }

    public void setEspeciaisDiversosId(Long especiaisDiversosId) {
        this.especiaisDiversosId = especiaisDiversosId;
    }

    public Long getCirurgiasLeitoId() {
        return cirurgiasLeitoId;
    }

    public void setCirurgiasLeitoId(Long cirurgiasLeitoId) {
        this.cirurgiasLeitoId = cirurgiasLeitoId;
    }

    public Long getOrteseProteseId() {
        return orteseProteseId;
    }

    public void setOrteseProteseId(Long orteseProteseId) {
        this.orteseProteseId = orteseProteseId;
    }

    public Long getPrescricaoProcedimentoEspecialId() {
        return prescricaoProcedimentoEspecialId;
    }

    public void setPrescricaoProcedimentoEspecialId(Long prescricaoProcedimentoEspecialId) {
        this.prescricaoProcedimentoEspecialId = prescricaoProcedimentoEspecialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO = (ItemPrescricaoProcedimentoEspecialDTO) o;
        if (itemPrescricaoProcedimentoEspecialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPrescricaoProcedimentoEspecialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPrescricaoProcedimentoEspecialDTO{" +
            "id=" + getId() +
            ", tipoProcedimento='" + getTipoProcedimento() + "'" +
            ", quantidadeOrteseProtese=" + getQuantidadeOrteseProtese() +
            ", informacoes='" + getInformacoes() + "'" +
            ", justificativa='" + getJustificativa() + "'" +
            ", duracaoSolicitada=" + getDuracaoSolicitada() +
            ", especiaisDiversos=" + getEspeciaisDiversosId() +
            ", cirurgiasLeito=" + getCirurgiasLeitoId() +
            ", orteseProtese=" + getOrteseProteseId() +
            ", prescricaoProcedimentoEspecial=" + getPrescricaoProcedimentoEspecialId() +
            "}";
    }
}
