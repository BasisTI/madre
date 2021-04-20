package br.com.basis.madre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

    public final static String PREFIX = "templates/";
    public final static String ENCODING = "UTF-8";
    public final static String MODE_HTML = "HTML5";
    public final static String MODE_XHTML = "XHTML";
    public final static boolean CACHEABLE = false;
    public final static String SUFFIX = ".html";

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(PREFIX);
        templateResolver.setCharacterEncoding(ENCODING);
        templateResolver.setTemplateMode(MODE_HTML);
        templateResolver.setCacheable(CACHEABLE);
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
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }
}
