package br.com.basis.madre.service.stream;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.evento.EventoPaciente;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class PacienteConsumer implements Consumer<Message<EventoPaciente>> {
    private final PacienteSearchRepository pacienteSearchRepository;

    @Override
    public void accept(Message<EventoPaciente> message) {
        Paciente paciente = message.getPayload().getPaciente();
        log.debug("Mensagem recebida = {}", paciente);
        pacienteSearchRepository.save(paciente);
    }
}
