package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;

import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import lombok.Data;

@Data
public class EventoPrescricaoMedicamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private PrescricaoMedicamento prescricaoMedicamento;
	

}
