package com.infinitechnic;

import com.infinitechnic.config.AppConfig;
import com.mongodb.MongoClient;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher extends SpringBootServletInitializer {
    public static void main( String[] args ) throws Exception {
        SpringApplication.run(Launcher.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Launcher.class);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, AppConfig.class.getName());
        return registration;
    }

    @Bean
    public EmbeddedServletContainerFactory containerFactory() {
        final JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory() {
            @Override
            protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {
                return new JettyEmbeddedServletContainer(server);
            }
        };
//        jettyEmbeddedServletContainerFactory.addServerCustomizers(new JettyConfigurer());
        return jettyEmbeddedServletContainerFactory;
    }

    @Bean
    public MongoClient mongoClient() {
        //TODO: put localhost & 27017 in config file
        return new MongoClient("localhost", 27017);
    }

    @Bean
    public Datastore datastore(MongoClient mongoClient) {
        //TODO: put the packages & dbName in config file
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.infinitechnic.horseracing.data.hkjc");
        Datastore datastore = morphia.createDatastore(mongoClient, "horseracing");
        datastore.ensureIndexes();
        return datastore;
    }
}
