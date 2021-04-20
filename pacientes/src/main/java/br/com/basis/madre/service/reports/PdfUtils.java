package br.com.basis.madre.service.reports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class PdfUtils {

    private static SpringTemplateEngine templateEngine;

    public PdfUtils(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String converterParaXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    public static SpringTemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
