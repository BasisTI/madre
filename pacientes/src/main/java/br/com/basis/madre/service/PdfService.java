package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.service.reports.PdfUtils;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class PdfService {

    private final Logger log = LoggerFactory.getLogger(PdfService.class);
    private PacienteRepository pacienteRepository;
    private PdfUtils pdfUtils;

    public PdfService(PacienteRepository pacienteRepository, PdfUtils pdfUtils){
        this.pacienteRepository = pacienteRepository;
        this.pdfUtils = pdfUtils;
    }

    @Transactional(readOnly = true)
    public byte[] getPdfByPacienteId(Long id) throws IOException, DocumentException {
        log.debug("Request to get pdf for Paciente id : {}", id);
        Paciente obj = pacienteRepository.findById(id).get();
        return pdfUtils.generatePdfByPaciente(obj);
    }



}
