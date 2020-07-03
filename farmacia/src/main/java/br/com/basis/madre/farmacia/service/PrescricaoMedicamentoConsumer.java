package br.com.basis.madre.farmacia.service;


import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.domain.evento.EventoPrescricao;
import br.com.basis.madre.farmacia.domain.evento.ItemPrescricaoMedicamentos;
import br.com.basis.madre.farmacia.repository.search.MedicamentoSearchRepository;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Slf4j
@RequiredArgsConstructor
@Component
public class PrescricaoMedicamentoConsumer implements Consumer<Message<EventoPrescricao>> {

    private final PrescricaoSearchRepository repository;
    private final MedicamentoSearchRepository medicamentoSearchRepository;
    private final DispensacaoService dispensacaoService;


    @SneakyThrows
    @Override
    public void accept(Message<EventoPrescricao> message) {
        EventoPrescricao evento = message.getPayload();
        log.debug("Mensagem recebida = {}", evento);
        Prescricao prescricao = new Prescricao();
        prescricao.setNome(evento.getPaciente().getNome());
        prescricao.setDataInicio(evento.getDataDeLancamento().toLocalDate());
        prescricao.setId(evento.getPaciente().getId());

        List<Long> idMedicamento = new ArrayList<>();
        for (ItemPrescricaoMedicamentos itemPrescricaoMedicamento : evento.getPrescricaoMedicamento().getItemPrescricaoMedicamentos()) {
            idMedicamento.add(itemPrescricaoMedicamento.getIdMedicamento());
        }

        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        Iterable<Medicamento> iterableMedi = medicamentoSearchRepository.findAllById(idMedicamento);
        iterableMedi.forEach(medicamentos::add);
        prescricao.setMedicamentos(medicamentos);

        DispensacaoDTO dispensacao = new DispensacaoDTO();
        dispensacao.setIdPrescricao(evento.getPaciente().getId());

    DispensacaoDTO rusult = dispensacaoService.save(dispensacao);

        prescricao.setIdDispensacao(rusult.getId());
        repository.save(prescricao);

    }
}
