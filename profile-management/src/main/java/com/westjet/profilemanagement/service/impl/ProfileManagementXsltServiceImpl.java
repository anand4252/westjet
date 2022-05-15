package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.config.TransformerConfig;
import com.westjet.profilemanagement.service.XsltService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileManagementXsltServiceImpl implements XsltService {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final TransformerFactory transformerFactory;
    private final TransformerConfig transformerConfig;

    @Override
    public void transform() {
        transformerConfig.getNodes()
                .forEach(node ->
                {
                    try (InputStream employeesInputStream = new FileInputStream(node.getInputPath());
                         FileOutputStream output = new FileOutputStream(node.getOutputPath())) {
                        DocumentBuilder db = this.documentBuilderFactory.newDocumentBuilder();
                        Document doc = db.parse(employeesInputStream);

                        transform(doc, output, node.getXsltPath());

                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
                        log.error("Error while processing the node {}. Error: {}", node, e);
                    }
                });

    }

    private void transform(Document doc, OutputStream output, String transformerPath) throws TransformerException {
        val streamSource = new StreamSource(new File(transformerPath));
        Transformer transformer = this.transformerFactory.newTransformer(streamSource);
        transformer.transform(new DOMSource(doc), new StreamResult(output));
    }

}
