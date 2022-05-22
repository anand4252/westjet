package com.westjet.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;

@Configuration
public class AppConfig {

    @Bean
    public DocumentBuilderFactory documentBuilderFactory() {
        return DocumentBuilderFactory.newInstance();
    }
    @Bean
    public TransformerFactory transformerFactory() {
        return TransformerFactory.newInstance();
    }

    @Bean
    public XPathFactory xPathFactory() {
        return XPathFactory.newInstance();
    }
}
