package br.com.basis.madre.pacientes.domain.dto;

public class PacienteDTO {

    private Integer total;

    public PacienteDTO(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PacienteDTO{" +
            "total=" + total +
            '}';
    }
}
