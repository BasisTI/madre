package br.com.basis.madre.service.event.listener;

import br.com.basis.madre.domain.evento.EventoPaciente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.EmitterProcessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class PacienteEventListener {

    private final EmitterProcessor<EventoPaciente> pacienteEmitterProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processarEventoPaciente(EventoPaciente eventoPaciente) {
        log.debug("Paciente criado, enviando mensagem para o broker: {}", eventoPaciente.getPaciente());
        pacienteEmitterProcessor.onNext(eventoPaciente);
    }
}
