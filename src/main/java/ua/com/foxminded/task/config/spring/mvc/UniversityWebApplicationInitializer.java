package ua.com.foxminded.task.config.spring.mvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class UniversityWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext annotationConfig = new AnnotationConfigWebApplicationContext();
        annotationConfig.register(WebConfig.class);

        // Create and register the DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(annotationConfig);
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("dispatcher", dispatcherServlet);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/*");

    }

}
