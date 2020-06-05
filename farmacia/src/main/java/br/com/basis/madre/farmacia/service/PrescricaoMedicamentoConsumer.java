package br.com.basis.madre.farmacia.service;



import br.com.basis.madre.farmacia.repository.search.PrescricaoSerchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Slf4j
@RequiredArgsConstructor
@Component
public class PrescricaoMedicamentoConsumer implements Consumer<Message<String>> {

    private final PrescricaoSerchRepository repository;

    @Override
    public void accept(Message<String> message) {
        log.info(message.getPayload().toString());

    }
}
