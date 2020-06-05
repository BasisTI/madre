package br.com.basis.madre.prescricao.service.listener;

import org.springframework.stereotype.Component;

import br.com.basis.madre.prescricao.domain.evento.EventoPrescricaoMedicamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class PrescricaoEventListener {

	private EmitterProcessor<EventoPrescricaoMedicamento> prescricaoEmitterProcessor;

	public void processarEventoPrescricao(EventoPrescricaoMedicamento eventoPrescricaoMedicamento) {
		log.debug("Paciente criado, enviando mensagem para o broker: {}",
				eventoPrescricaoMedicamento.getPrescricaoMedicamento());
		prescricaoEmitterProcessor.onNext(eventoPrescricaoMedicamento);
	}

}
