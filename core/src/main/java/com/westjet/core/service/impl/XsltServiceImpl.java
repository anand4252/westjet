package com.westjet.core.service.impl;

import com.westjet.core.helper.CoreHelper;
import com.westjet.core.model.TransferDetails;
import com.westjet.core.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service("xsltService")
@Slf4j
@RequiredArgsConstructor
public class XsltServiceImpl implements CoreService {
    private final CoreHelper coreHelper;

    public static final String XSLT_PATH = "xsltPath";

    private final TransformerFactory transformerFactory;

    public TransferDetails perform(TransferDetails transferDetails, Map<String, String> properties) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        ByteArrayOutputStream byteArrayOutputStream = transformXml(transferDetails, properties);
        final String transformedXml = byteArrayOutputStream.toString();

        final List<Map<String, Object>> paramsFromPreviousService = transferDetails.getParams();
        // Add new params for the next service if any.

        return new TransferDetails(transformedXml, paramsFromPreviousService);
    }

    private ByteArrayOutputStream transformXml(TransferDetails transferDetails, Map<String, String> properties) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document xmlDocument = coreHelper.getDocument(transferDetails);
        Transformer transformer = getTransformer(properties);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(byteArrayOutputStream));
        return byteArrayOutputStream;
    }


    private Transformer getTransformer(Map<String, String> properties) throws TransformerConfigurationException {
        val streamSource = new StreamSource(new File(properties.get(XSLT_PATH)));
        return this.transformerFactory.newTransformer(streamSource);
    }
}
