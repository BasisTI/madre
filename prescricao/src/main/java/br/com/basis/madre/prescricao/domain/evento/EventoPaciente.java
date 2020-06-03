package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;

import br.com.basis.madre.prescricao.domain.Paciente;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventoPaciente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Paciente paciente;

}
