package br.com.basis.madre.service.reports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Component
public class PdfService {

    private static SpringTemplateEngine templateEngine;

    public PdfService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String converterParaXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    public Context definirContexto(Map<String, Object> variavelTemplateETipo){
        Context context = new Context();
        context.setVariables(variavelTemplateETipo);
        return context;
    }

    public String processarHtml(String variavelTemplate, Context context){
        return templateEngine.process(variavelTemplate, context);
    }

    public static SpringTemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
