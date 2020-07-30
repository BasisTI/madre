package br.com.basis.madre.farmacia.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.Estorno} entity.
 */
public class EstornoDTO implements Serializable {

    private Long id;

    private Boolean estornado;

    private Long usuarioQueEstornou;

    private LocalDate dataDeEstorno;


    private Long dispensacaoMedicamentosId;

    private MotivoDTO motivoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstornado() {
        return estornado;
    }

    public void setEstornado(Boolean estornado) {
        this.estornado = estornado;
    }

    public Long getUsuarioQueEstornou() {
        return usuarioQueEstornou;
    }

    public void setUsuarioQueEstornou(Long usuarioQueEstornou) {
        this.usuarioQueEstornou = usuarioQueEstornou;
    }

    public LocalDate getDataDeEstorno() {
        return dataDeEstorno;
    }

    public void setDataDeEstorno(LocalDate dataDeEstorno) {
        this.dataDeEstorno = dataDeEstorno;
    }

    public Long getDispensacaoMedicamentosId() {
        return dispensacaoMedicamentosId;
    }

    public void setDispensacaoMedicamentosId(Long dispensacaoMedicamentosId) {
        this.dispensacaoMedicamentosId = dispensacaoMedicamentosId;
    }

    public MotivoDTO getMotivoId() {
        return motivoId;
    }

    public void setMotivoId(MotivoDTO motivoId) {
        this.motivoId = motivoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstornoDTO estornoDTO = (EstornoDTO) o;
        if (estornoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estornoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstornoDTO{" +
            "id=" + getId() +
            ", estornado='" + isEstornado() + "'" +
            ", usuarioQueEstornou=" + getUsuarioQueEstornou() +
            ", dataDeEstorno='" + getDataDeEstorno() + "'" +
            ", dispensacaoMedicamentos=" + getDispensacaoMedicamentosId() +
            ", motivo=" + getMotivoId() +
            "}";
    }
}
