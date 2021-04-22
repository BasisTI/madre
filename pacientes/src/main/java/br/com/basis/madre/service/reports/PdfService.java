package br.com.basis.madre.service.reports;

import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
public class PdfService {

    private SpringTemplateEngine templateEngine;

    public PdfService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] gerarPdf(Map<String, Object> mapContexto, String nomeArquivoHtml, String urlRecursos) throws DocumentException {
        return renderizarPdf(converterParaXhtml(processarHtml(nomeArquivoHtml, definirContexto(mapContexto))), urlRecursos);
    }

    private Context definirContexto(Map<String, Object> variavelTemplateETipo){
        Context context = new Context();
        context.setVariables(variavelTemplateETipo);
        return context;
    }

    private String processarHtml(String nomeArquivoHtml, Context context){
        return templateEngine.process(nomeArquivoHtml, context);
    }

    private String converterParaXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private byte[] renderizarPdf(String xHtml, String urlRecursos) throws DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xHtml, urlRecursos);
        renderer.layout();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        renderer.createPDF(byteArray);

        return byteArray.toByteArray();
    }

    public SpringTemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
