package com.westjet.profilemanagement.controller;

import com.westjet.profilemanagement.exception.InvalidInputXmlException;
import com.westjet.profilemanagement.service.XsltService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@RestController
@RequestMapping("/profilemanagement")
@RequiredArgsConstructor
@Slf4j
public class ProfileManagementController {

    private final XsltService xsltService;

    @GetMapping
    public String start() {
        long startTime = System.currentTimeMillis();
        System.out.println("Process started!");
        try {
            xsltService.transform();
        } catch (InvalidInputXmlException e) {
            log.error("Input xml is invalid.");
        } catch ( IOException | ParserConfigurationException | TransformerException | SAXException e) {
            log.error("Error while applying XSL transformation.");
        }

        final long timeTaken = System.currentTimeMillis() - startTime;
        return "Process Completed in ms: " + timeTaken;
    }

}
