package br.com.basis.madre.config;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    public static final String PREFIX = "templates/";
    public static final String ENCODING = "UTF-8";
    public static final String MODE_HTML = "HTML5";
    public static final String MODE_XHTML = "XHTML";
    public static final String SUFFIX = ".html";

    private ThymeleafProperties properties;

    public ThymeleafConfiguration(ThymeleafProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(PREFIX);
        templateResolver.setCharacterEncoding(ENCODING);
        templateResolver.setTemplateMode(MODE_HTML);
        templateResolver.setCacheable(properties.isCache());
        templateResolver.setSuffix(SUFFIX);
        templateResolver.setTemplateMode(MODE_XHTML);

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding(ENCODING);
        return viewResolver;
    }
}
