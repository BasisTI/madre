package br.com.basis.madre.service.reports;
import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.domain.Paciente;
import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class PdfUtils {

    private static SpringTemplateEngine templateEngine;

    public PdfUtils(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdfByPaciente(Paciente obj) throws IOException, DocumentException {

        Paciente paciente = validationFields(obj);

        Context context = new Context();
        context.setVariable("paciente", paciente);
        context.setVariable("idade", ageCalculate(paciente.getDataDeNascimento()));
        String html = templateEngine.process("paciente", context);
        String xHtml = convertToXhtml(html);
        String url = this.getClass().getClassLoader().getResource("templates/").toString();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xHtml, url);
        renderer.layout();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        renderer.createPDF(byteArray);

        return byteArray.toByteArray();
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
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
