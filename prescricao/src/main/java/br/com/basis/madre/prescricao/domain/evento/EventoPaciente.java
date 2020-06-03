package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;

import br.com.basis.madre.prescricao.domain.Paciente;
import lombok.Data;
@Data
public class EventoPaciente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Paciente paciente;

}
