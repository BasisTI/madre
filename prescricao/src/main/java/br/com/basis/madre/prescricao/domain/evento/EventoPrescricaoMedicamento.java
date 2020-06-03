package br.com.basis.madre.prescricao.domain.evento;

import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;


public class EventoPrescricaoMedicamento{
	
	private PrescricaoMedicamento prescricaoMedicamento;
	
	public EventoPrescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
		this.prescricaoMedicamento = prescricaoMedicamento;
	}

	public PrescricaoMedicamento getPrescricaoMedicamento() {
		return prescricaoMedicamento;
	}

	public void setPrescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
		this.prescricaoMedicamento = prescricaoMedicamento;
	}
	
	

}
