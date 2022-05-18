package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.config.TransformerConfig;
import com.westjet.profilemanagement.exception.InvalidInputXmlException;
import com.westjet.profilemanagement.model.Node;
import com.westjet.profilemanagement.service.XsltService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.westjet.profilemanagement.model.Node.XSLT_PATH;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileManagementXsltServiceImpl implements XsltService {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final TransformerFactory transformerFactory;
    private final TransformerConfig transformerConfig;

    @Override
    public String getInputXml() throws InvalidInputXmlException {
        val inputPath = transformerConfig.getInputXmlPath();
        Path filePath = Path.of(inputPath);
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading input xml.", e);
        }

        if (!StringUtils.hasText(content)) {
            throw new InvalidInputXmlException("Input xml is empty.");
        }
        log.info("Input xml:: " + content);

        return content;
    }

    @Override
    public void transform() throws InvalidInputXmlException, IOException, ParserConfigurationException, TransformerException, SAXException {
        final List<Node> nodes = transformerConfig.getNodes();
        final String inputXml = getInputXml();
        String transformationResult = transform(inputXml, nodes.get(0).getProperties().get(XSLT_PATH));

        //until last but one transformation
        for (int i = 1; i < nodes.size(); i++) {
            log.info("~~~~Transformed result:: " + transformationResult);
            transformationResult = transform(transformationResult, nodes.get(i).getProperties().get(XSLT_PATH));
        }

        log.info("~~~~Transformed result:: " + transformationResult);
    }

    private String transform(String inputXml, String transformerPath) throws IOException, SAXException, TransformerException, ParserConfigurationException {
        DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(inputXml.getBytes()));

        val streamSource = new StreamSource(new File(transformerPath));
        Transformer transformer = this.transformerFactory.newTransformer(streamSource);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(byteArrayOutputStream));

        return byteArrayOutputStream.toString();
    }

}
