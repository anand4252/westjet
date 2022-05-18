package com.westjet.profilemanagement.service;

import com.westjet.profilemanagement.exception.InvalidInputXmlException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface XsltService {

    String getInputXml() throws IOException, InvalidInputXmlException;

    void transform() throws InvalidInputXmlException, IOException, ParserConfigurationException, TransformerException, SAXException;
}
