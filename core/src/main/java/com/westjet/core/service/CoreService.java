package com.westjet.core.service;

import com.westjet.core.model.TransferDetails;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Map;

public interface CoreService {

    TransferDetails perform(TransferDetails transferDetails, Map<String, String> properties) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException, TransformerException;
}
