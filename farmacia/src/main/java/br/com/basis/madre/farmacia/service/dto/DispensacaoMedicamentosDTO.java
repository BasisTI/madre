package br.com.basis.madre.farmacia.service.dto;
import br.com.basis.madre.farmacia.domain.Medicamento;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos} entity.
 */
public class DispensacaoMedicamentosDTO implements Serializable {

    private Long id;

    private Long idFarmacia;

    private Boolean dispensado;
    private Boolean estornado;

    private Long usuarioQueDispensou;


    private Long dispensacaoId;

    private MedicamentoDTO medicamentosId;

    public Boolean getEstornado() {
        return estornado;
    }

    public void setEstornado(Boolean estornado) {
        this.estornado = estornado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(Long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public Boolean isDispensado() {
        return dispensado;
    }

    public void setDispensado(Boolean dispensado) {
        this.dispensado = dispensado;
    }

    public Long getUsuarioQueDispensou() {
        return usuarioQueDispensou;
    }

    public void setUsuarioQueDispensou(Long usuarioQueDispensou) {
        this.usuarioQueDispensou = usuarioQueDispensou;
    }

    public Long getDispensacaoId() {
        return dispensacaoId;
    }

    public void setDispensacaoId(Long dispensacaoId) {
        this.dispensacaoId = dispensacaoId;
    }

    public MedicamentoDTO getMedicamentosId() {
        return medicamentosId;
    }

    public void setMedicamentosId(MedicamentoDTO medicamentoId) {
        this.medicamentosId = medicamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = (DispensacaoMedicamentosDTO) o;
        if (dispensacaoMedicamentosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispensacaoMedicamentosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispensacaoMedicamentosDTO{" +
            "id=" + id +
            ", idFarmacia=" + idFarmacia +
            ", dispensado=" + dispensado +
            ", estornado=" + estornado +
            ", usuarioQueDispensou=" + usuarioQueDispensou +
            ", dispensacaoId=" + dispensacaoId +
            ", medicamentosId=" + medicamentosId +
            '}';
    }
}
