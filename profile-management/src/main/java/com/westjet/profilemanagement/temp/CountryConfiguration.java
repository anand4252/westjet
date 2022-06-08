
package com.westjet.profilemanagement.temp;

import com.westjet.profilemanagement.SessionClient;
import com.westjet.profilemanagement.builder.SessionCreateBuilder;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CountryConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.westjet.profilemanagement.model.wsdl");
        marshaller.setMarshallerProperties(buildMarshallerProperties());

        return marshaller;
    }

    @Bean
    public CountryClient countryClient(Jaxb2Marshaller marshaller) {
        CountryClient client = new CountryClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public SessionClient sessionClient(Jaxb2Marshaller marshaller, SessionCreateBuilder sessionCreateService) {
        SessionClient client = new SessionClient(sessionCreateService);
        client.setDefaultUri("https://sws-crt-as.cert.havail.sabre.com/");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }

    private Map<String, Boolean> buildMarshallerProperties() {
        final Map<String, Boolean> marshallerProperties = new HashMap<>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshallerProperties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        return marshallerProperties;
    }

}
