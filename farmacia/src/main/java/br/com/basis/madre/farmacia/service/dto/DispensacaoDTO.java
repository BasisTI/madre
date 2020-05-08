package br.com.basis.madre.farmacia.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.Dispensacao} entity.
 */
public class DispensacaoDTO implements Serializable {

    private Long id;

    private Long idPrescricao;

    private Long idUnidade;

    private Long usuarioQueCriou;

    private LocalDate dataDeCriacao;

    private Long usuarioQueAlterou;

    private LocalDate dataDeAlteracao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Long idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getUsuarioQueCriou() {
        return usuarioQueCriou;
    }

    public void setUsuarioQueCriou(Long usuarioQueCriou) {
        this.usuarioQueCriou = usuarioQueCriou;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public Long getUsuarioQueAlterou() {
        return usuarioQueAlterou;
    }

    public void setUsuarioQueAlterou(Long usuarioQueAlterou) {
        this.usuarioQueAlterou = usuarioQueAlterou;
    }

    public LocalDate getDataDeAlteracao() {
        return dataDeAlteracao;
    }

    public void setDataDeAlteracao(LocalDate dataDeAlteracao) {
        this.dataDeAlteracao = dataDeAlteracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DispensacaoDTO dispensacaoDTO = (DispensacaoDTO) o;
        if (dispensacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispensacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispensacaoDTO{" +
            "id=" + getId() +
            ", idPrescricao=" + getIdPrescricao() +
            ", idUnidade=" + getIdUnidade() +
            ", usuarioQueCriou=" + getUsuarioQueCriou() +
            ", dataDeCriacao='" + getDataDeCriacao() + "'" +
            ", usuarioQueAlterou=" + getUsuarioQueAlterou() +
            ", dataDeAlteracao='" + getDataDeAlteracao() + "'" +
            "}";
    }
}
