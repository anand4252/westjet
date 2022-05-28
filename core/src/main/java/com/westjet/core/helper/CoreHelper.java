package com.westjet.core.helper;

import com.westjet.core.exception.InvalidInputXmlException;
import com.westjet.core.model.TransferDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Slf4j
@RequiredArgsConstructor
public class CoreHelper {
    private final DocumentBuilderFactory documentBuilderFactory;

    public String getInputXml(String xmlPath) throws InvalidInputXmlException {
        Path filePath = Path.of(xmlPath);
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading input xml.", e);
        }

        if (!StringUtils.hasText(content)) {
            throw new InvalidInputXmlException("Input xml is empty.");
        }
        log.info("Input xml:: " + content);

        return content;
    }

    public Document getDocument(TransferDetails transferDetails) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(new ByteArrayInputStream(transferDetails.getInput().getBytes()));
    }


}
