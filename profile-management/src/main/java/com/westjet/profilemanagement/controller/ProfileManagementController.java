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
    private static final String EMPLOYEES_INPUT_XML = "src/main/resources/xml/input/employees-input.xml";
    private static final String XERRIS_INTERMEDIATE_XML = "src/main/resources/xml/intermediate/xerris.xml";
    private static final String XERRIS_LOCATION_OUTPUT_XML = "src/main/resources/xml/output/xerris-location.xml";
    private static final String XERRIS_TRANSFORMER = "src/main/resources/xslt/xerris-transformer.xslt";
    private static final String XERRIS_LOCATION_TRANSFORMER = "src/main/resources/xslt/xerris-location-transformer.xslt";

    @GetMapping
    public String start() {
        System.out.println("Process started!");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream employeesInputStream = new FileInputStream(EMPLOYEES_INPUT_XML);
             FileOutputStream output = new FileOutputStream(XERRIS_INTERMEDIATE_XML)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(employeesInputStream);

            transform(doc, output, XERRIS_TRANSFORMER);

        } catch (IOException | ParserConfigurationException |
                 SAXException | TransformerException e) {
            e.printStackTrace();
        }

        try (InputStream employeesInputStream = new FileInputStream(XERRIS_INTERMEDIATE_XML);
             FileOutputStream output = new FileOutputStream(XERRIS_LOCATION_OUTPUT_XML)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(employeesInputStream);

            transform(doc, output, XERRIS_LOCATION_TRANSFORMER);

        } catch (IOException | ParserConfigurationException |
                 SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return "Process Completed!";
    }


    private static void transform(Document doc, OutputStream output, String xerrisTransformerPath) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // add XSLT in Transformer
        Transformer xerrisTransformer = transformerFactory.newTransformer(
                new StreamSource(new File(xerrisTransformerPath)));

        xerrisTransformer.transform(new DOMSource(doc), new StreamResult(output));
    }
}
