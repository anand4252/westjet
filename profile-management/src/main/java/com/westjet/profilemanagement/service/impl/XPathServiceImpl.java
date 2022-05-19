package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("xPathService")
@Slf4j
@RequiredArgsConstructor
public class XPathServiceImpl implements CoreService {

    public static final String XPATH_EXPRESSION = "xpathExpression";

    private final DocumentBuilderFactory documentBuilderFactory;

    private final XPathFactory xPathFactory;

    @Override
    public String perform(String inputXml, Map<String, String> properties, List<String> placeholders) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        Document xmlDocument = documentBuilder.parse(new ByteArrayInputStream(inputXml.getBytes()));
        XPath xPath = xPathFactory.newXPath();
        final XPathExpression xPathExpression = xPath.compile(properties.get(XPATH_EXPRESSION));
        final NodeList nodeList = (NodeList) xPathExpression.evaluate(xmlDocument, XPathConstants.NODESET);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodeList.getLength(); i++) {
            stringBuilder.append(nodeList.item(i).getFirstChild().getNodeValue());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
