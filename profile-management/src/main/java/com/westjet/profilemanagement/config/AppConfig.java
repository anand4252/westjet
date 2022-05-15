package com.westjet.profilemanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;

@Configuration
public class AppConfig {

    @Bean
    public TransformerFactory transformerFactory(){
        return TransformerFactory.newInstance();
    }

    @Bean
    public DocumentBuilderFactory documentBuilderFactory(){
        return DocumentBuilderFactory.newInstance();
    }
}
