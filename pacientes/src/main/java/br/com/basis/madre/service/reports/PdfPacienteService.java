package br.com.basis.madre.service.reports;

import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.PacienteRepository;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PdfPacienteService {

    private final Logger log = LoggerFactory.getLogger(PdfPacienteService.class);
    private PacienteRepository pacienteRepository;
    private PdfService pdfService;

    public PdfPacienteService(PacienteRepository pacienteRepository, PdfService pdfService){
        this.pacienteRepository = pacienteRepository;
        this.pdfService = pdfService;
    }

    @Transactional(readOnly = true)
    public byte[] getPdfPorPacienteId(Long id) throws DocumentException {
        log.debug("Request to get pdf for Paciente id : {}", id);
        Paciente obj = pacienteRepository.findById(id).get();
        return gerarPdfParaPaciente(obj);
    }

    public byte[] gerarPdfParaPaciente(Paciente obj) throws DocumentException {
        Paciente paciente = validationFields(obj);
        Map<String, Object> variaveisTemplate = new HashMap<>();
        variaveisTemplate.put("paciente", paciente);
        variaveisTemplate.put("idade", calcularIdade(paciente.getDataDeNascimento()));

        String url = this.getClass().getClassLoader().getResource("templates/").toString();

        return pdfService.gerarPdf(variaveisTemplate, "paciente", url);
    }

    private Paciente validationFields(Paciente paciente){
        String prontuarioDaMae = paciente.getGenitores().getProntuarioDaMae() == null ? "" : paciente.getGenitores().getProntuarioDaMae();
        String cnh = paciente.getDocumento().getCnh() == null ? "" : paciente.getDocumento().getCnh();
        Justificativa justificativa = paciente.getCartaoSUS().getJustificativa() == null ? new Justificativa() : paciente.getCartaoSUS().getJustificativa();
        justificativa.setValor("");
        String portariaNaturalizacao = paciente.getCartaoSUS().getPortaria() == null ? "" : paciente.getCartaoSUS().getPortaria();
        String observacao = paciente.getObservacao() == null ? "" : paciente.getObservacao();

        paciente.getGenitores().setProntuarioDaMae(prontuarioDaMae);
        paciente.getDocumento().setCnh(cnh);
        paciente.getCartaoSUS().setJustificativa(justificativa);
        paciente.getCartaoSUS().setPortaria(portariaNaturalizacao);
        paciente.setObservacao(observacao);

        return paciente;
    }

    private int calcularIdade(LocalDate dataNascimento){
        LocalDate now = LocalDate.now();
        Period diff = Period.between(dataNascimento, now);
        return diff.getYears();
    }

}
