package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.config.TransformerConfig;
import com.westjet.profilemanagement.model.Node;
import com.westjet.profilemanagement.service.XsltService;
import com.westjet.profilemanagement.utils.ByteArrayInOutStream;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileManagementXsltServiceImpl implements XsltService {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final TransformerFactory transformerFactory;
    private final TransformerConfig transformerConfig;

    @Override
    public void transform() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        final byte[] bytes = getInputXml();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        final List<Node> nodes = transformerConfig.getNodes();
        //First transformation
        ByteArrayInOutStream byteArrayInOutStream = extracted(nodes.get(0), byteArrayInputStream);
        System.out.println("11111111111 : " + byteArrayInOutStream);
        byteArrayInputStream = byteArrayInOutStream.getInputStream();

        //until last but one transformation
        for (int i = 1; i < nodes.size()-1; i++) {
            byteArrayInOutStream = extracted(nodes.get(i), byteArrayInputStream);
            final String transformedXml = byteArrayInOutStream.toString();
            System.out.println("!!!!!!!!!!!!!!!! " + transformedXml);
            byteArrayInputStream = byteArrayInOutStream.getInputStream();
        }

        //last transformation
        byteArrayInOutStream = extracted(nodes.get(nodes.size()-1), byteArrayInputStream);
        System.out.println("2222222222  " + byteArrayInOutStream);


//        transformerConfig.getNodes()
//                .forEach(node ->
//                {
//                    try (InputStream employeesInputStream = new FileInputStream(node.getInputPath());
//                         FileOutputStream output = new FileOutputStream(node.getOutputPath())) {
//                        DocumentBuilder db = this.documentBuilderFactory.newDocumentBuilder();
//                        Document doc = db.parse(employeesInputStream);
//
//                        transform(doc, output, node.getXsltPath());
//
//                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
//                        log.error("Error while processing the node {}. Error: {}", node, e);
//                    }
//                });

    }

    private byte[] getInputXml() throws IOException {
        val inputPath = "src/main/resources/xml/input/employees-input.xml"; //TODO move to config
        final Path path = Paths.get(inputPath);
        return Files.readAllBytes(path);
    }

    private ByteArrayInOutStream extracted(Node node, ByteArrayInputStream byteArrayInputStream) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilder db = this.documentBuilderFactory.newDocumentBuilder();
        Document doc = db.parse(byteArrayInputStream);
        ByteArrayInOutStream byteArrayInOutStream = new ByteArrayInOutStream();
        transform(doc, byteArrayInOutStream, node.getProperties().get(Node.XSLT_PATH));
        return byteArrayInOutStream;
    }

    private void transform(Document doc, ByteArrayOutputStream output, String transformerPath) throws TransformerException {
        val streamSource = new StreamSource(new File(transformerPath));
        Transformer transformer = this.transformerFactory.newTransformer(streamSource);
        transformer.transform(new DOMSource(doc), new StreamResult(output));
    }

}
