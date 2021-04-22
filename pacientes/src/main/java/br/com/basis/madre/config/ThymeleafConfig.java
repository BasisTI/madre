package br.com.basis.madre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

    public static final String PREFIX = "templates/";
    public static final String ENCODING = "UTF-8";
    public static final String MODE_HTML = "HTML5";
    public static final String MODE_XHTML = "XHTML";
    public static final String SUFFIX = ".html";

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(PREFIX);
        templateResolver.setCharacterEncoding(ENCODING);
        templateResolver.setTemplateMode(MODE_HTML);
        templateResolver.setCacheable(false);
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
