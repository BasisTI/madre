package br.com.basis.madre.prescricao.service;

import java.util.function.Consumer;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.domain.evento.EventoPaciente;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class PacienteConsumer implements Consumer<Message<EventoPaciente>> {
	private final PacienteRepositorySearch pacienteRepositorySearch;

	@Override
	public void accept(Message<EventoPaciente> pacienteMessage) {
		Paciente paciente = pacienteMessage.getPayload().getPaciente();
		log.debug("Mensagem recebida = {}", paciente);
		pacienteRepositorySearch.save(paciente);
		
	}

}
