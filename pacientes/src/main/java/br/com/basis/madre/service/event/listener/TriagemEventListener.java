package br.com.basis.madre.service.event.listener;

import br.com.basis.madre.domain.evento.EventoTriagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.EmitterProcessor;

@Slf4j
@RequiredArgsConstructor
@Component
public class TriagemEventListener {

    private final EmitterProcessor<EventoTriagem> triagemEmitterProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processarEventoTriagem(EventoTriagem eventoTriagem) {
        log.debug("Triagem criada, enviando mensagem para o broker: {}", eventoTriagem.getTriagem());
        triagemEmitterProcessor.onNext(eventoTriagem);
    }
}
