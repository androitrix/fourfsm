package nz.co.econz.fourfsm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"nz.co.econz.fourfsm.web"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
	@Bean TemplateResolver templateResolver() {
		
		ServletContextTemplateResolver tr = new ServletContextTemplateResolver();
		tr.setPrefix("/WEB-INF/templates/");
		tr.setSuffix(".html");
		tr.setTemplateMode("HTML5");
		return tr;
	}
	
	@Bean SpringTemplateEngine springtemplateEngine() {
		SpringTemplateEngine ste = new SpringTemplateEngine();
		ste.setTemplateResolver(templateResolver());
		return ste;
	}
	
    @Bean ViewResolver viewResolver() {
    	ThymeleafViewResolver v = new ThymeleafViewResolver();
        v.setTemplateEngine(springtemplateEngine());
        v.setOrder(1);
        return v;
    }
}
