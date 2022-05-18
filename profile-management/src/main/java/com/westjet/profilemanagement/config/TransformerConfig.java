package com.westjet.profilemanagement.config;

import com.westjet.profilemanagement.model.Node;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties
@Data
@Component
public class TransformerConfig {

    @Value("inputXmlPath")
    private String inputXmlPath;

    private List<Node> nodes;

}
