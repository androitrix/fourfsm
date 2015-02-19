package nz.co.econz.fourfsm.configuration;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class ThreeFsmInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(ThreeFsmInitializer.class.getName());

    public void onStartup(ServletContext servletContext)
            throws ServletException {
        // Create the root appcontext
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.register(RootConfig.class);
        // since we registered RootConfig instead of passing it to the
        // constructor
        ctx.refresh();

        // Manage the lifecycle of the root appcontext
        servletContext.addListener(new ContextLoaderListener(ctx));
        servletContext.setInitParameter("defaultHtmlEscape", "true");

        FilterRegistration.Dynamic fr = servletContext.addFilter(
                "encodingFilter", new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");

        CommonsRequestLoggingFilter requestLoggingFilter = new CommonsRequestLoggingFilter();
        requestLoggingFilter.setIncludeQueryString(true);
        
        servletContext.addFilter("requestLoggingFilter",
                requestLoggingFilter);

        // now the config for the Dispatcher servlet
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(WebMvcConfig.class);
        
        // The main Spring MVC servlet.
        ServletRegistration.Dynamic appServlet = servletContext.addServlet(
                "appServlet", new DispatcherServlet(mvcContext));
        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping("/");

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                logger.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }
    }

}
