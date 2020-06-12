package br.com.basis.madre.prescricao.service.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.basis.madre.prescricao.domain.evento.EventoPrescricaoMedicamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class PrescricaoEventListener {

    
    private final EmitterProcessor<EventoPrescricaoMedicamento> prescricaoEmitterProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processarEventoPrescricao(EventoPrescricaoMedicamento eventoPrescricaoMedicamento) {
        log.debug("Prescrição medicamento criado, enviando mensagem para o broker: {}",
                eventoPrescricaoMedicamento.getPrescricaoMedicamento());
        prescricaoEmitterProcessor.onNext(eventoPrescricaoMedicamento);
    }

}
