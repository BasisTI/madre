package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.enumeration.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoPrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;
    private PrescricaoMedicamento prescricaoMedicamento;
    private Paciente paciente;
    private ZonedDateTime dataDeLancamento;
    private TipoEvento tipoDoEvento;
    private String login;

}
