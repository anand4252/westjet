package com.westjet.profilemanagement.service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface XsltService {

    void transform() throws IOException, ParserConfigurationException, TransformerException, SAXException;
}
