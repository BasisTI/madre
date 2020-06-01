package br.com.basis.madre.service.stream;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class PacienteConsumer implements Consumer<Message<Paciente>> {
    private final PacienteSearchRepository pacienteSearchRepository;

    @Override
    public void accept(Message<Paciente> pacienteMessage) {
        pacienteSearchRepository.save(pacienteMessage.getPayload());
    }
}
