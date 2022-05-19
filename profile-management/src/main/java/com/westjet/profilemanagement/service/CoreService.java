package com.westjet.profilemanagement.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CoreService {

    String perform(String input, Map<String, String> properties, List<String> placeholders) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException, TransformerException;
}
