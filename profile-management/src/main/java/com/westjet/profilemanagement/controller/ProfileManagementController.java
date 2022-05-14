package com.westjet.profilemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping("/profilemanagement")
public class ProfileManagementController {
    private static final String XML_FILENAME = "src/main/resources/xml/input/staff-simple.xml";
    private static final String XSLT_FILENAME = "src/main/resources/xslt/staff-xml-html.xslt";

    @GetMapping
    public String start() {
        System.out.println("Process started!");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(XML_FILENAME)) {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);

            // transform xml to html via a xslt file
            try (FileOutputStream output = new FileOutputStream("src/main/resources/xml/output/staff.html")) {
                transform(doc, output);
            }

        } catch (IOException | ParserConfigurationException |
                 SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return "Process Completed!";
    }


    private static void transform(Document doc, OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // add XSLT in Transformer
        Transformer transformer = transformerFactory.newTransformer(
                new StreamSource(new File(XSLT_FILENAME)));

        transformer.transform(new DOMSource(doc), new StreamResult(output));

    }
}
