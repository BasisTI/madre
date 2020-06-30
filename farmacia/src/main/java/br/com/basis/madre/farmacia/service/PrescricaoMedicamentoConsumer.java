package br.com.basis.madre.farmacia.service;



import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.domain.evento.EventoPrescricao;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Slf4j
@RequiredArgsConstructor
@Component
public class PrescricaoMedicamentoConsumer implements Consumer<Message<EventoPrescricao>> {

    private final PrescricaoSearchRepository repository;

    @Override
    public void accept(Message<EventoPrescricao> message) {
        EventoPrescricao evento = message.getPayload();
        log.debug("Mensagem recebida = {}", evento);
        Prescricao prescricao = new Prescricao();
        prescricao.setNome(evento.getPaciente().getNome());
        prescricao.setDataInicio(evento.getDataDeLancamento().toLocalDate());
        repository.save(prescricao);

    }
}
