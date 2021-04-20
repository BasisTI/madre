package br.com.basis.madre.service;

import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.service.reports.PdfUtils;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
public class PdfPacienteService {

    private final Logger log = LoggerFactory.getLogger(PdfPacienteService.class);
    private PacienteRepository pacienteRepository;
    private PdfUtils pdfUtils;

    public PdfPacienteService(PacienteRepository pacienteRepository, PdfUtils pdfUtils){
        this.pacienteRepository = pacienteRepository;
        this.pdfUtils = pdfUtils;
    }

    @Transactional(readOnly = true)
    public byte[] getPdfPorPacienteId(Long id) throws IOException, DocumentException {
        log.debug("Request to get pdf for Paciente id : {}", id);
        Paciente obj = pacienteRepository.findById(id).get();
        return gerarPdfParaPaciente(obj);
    }

    public byte[] gerarPdfParaPaciente(Paciente obj) throws IOException, DocumentException {
        Paciente paciente = validationFields(obj);
        Context context = new Context();
        context.setVariable("paciente", paciente);
        context.setVariable("idade", ageCalculate(paciente.getDataDeNascimento()));
        String html = pdfUtils.getTemplateEngine().process("paciente", context);
        String xHtml = pdfUtils.converterParaXhtml(html);
        String url = this.getClass().getClassLoader().getResource("templates/").toString();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xHtml, url);
        renderer.layout();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        renderer.createPDF(byteArray);

        return byteArray.toByteArray();
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

    private int ageCalculate(LocalDate dataNascimento){
        LocalDate now = LocalDate.now();
        Period diff = Period.between(dataNascimento, now);
        return diff.getYears();
    }

}
