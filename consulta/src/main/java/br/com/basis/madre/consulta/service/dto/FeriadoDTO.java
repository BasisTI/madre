package br.com.basis.madre.consulta.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeriadoDTO implements Serializable {

    private Long id;

    private LocalDateTime data;

    private String turno;

    private String diaSemana;

    private Boolean situacao;

    @Override
    public String toString() {
        return "FeriadoDTO{" +
            "id=" + id +
            ", data=" + data +
            ", turno='" + turno + '\'' +
            ", diaSemana='" + diaSemana + '\'' +
            ", situacao=" + situacao +
            '}';
    }
}
