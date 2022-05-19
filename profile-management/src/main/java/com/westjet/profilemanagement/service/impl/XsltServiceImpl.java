package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.service.CoreService;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service("xsltService")
@Slf4j
@RequiredArgsConstructor
public class XsltServiceImpl implements CoreService {

    public static final String XSLT_PATH = "xsltPath";
    private final DocumentBuilderFactory documentBuilderFactory;
    private final TransformerFactory transformerFactory;

    public String perform(String inputXml, Map<String, String> properties, List<String> placeholders) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(inputXml.getBytes()));

        val streamSource = new StreamSource(new File(properties.get(XSLT_PATH)));
        Transformer transformer = this.transformerFactory.newTransformer(streamSource);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(byteArrayOutputStream));

        return byteArrayOutputStream.toString();
    }
}
