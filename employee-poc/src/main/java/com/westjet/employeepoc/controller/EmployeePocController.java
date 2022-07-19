package com.westjet.employeepoc.controller;

import com.westjet.core.config.TransformerConfig;
import com.westjet.core.exception.InvalidInputXmlException;
import com.westjet.core.helper.CoreHelper;
import com.westjet.core.model.Node;
import com.westjet.core.model.TransferDetails;
import com.westjet.core.service.CoreService;
import com.westjet.core.service.CoreServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/employeepoc")
@RequiredArgsConstructor
@Slf4j
public class EmployeePocController {

    private final TransformerConfig transformerConfig;
    private final CoreHelper coreHelper;
    private final CoreServiceFactory coreServiceFactory;

    @GetMapping
    public String start() {
        long startTime = System.currentTimeMillis();
        System.out.println("Process started!");
        try {
            String inputXml = coreHelper.getInputXml(transformerConfig.getInputXmlPath());
            TransferDetails transferDetails = new TransferDetails(inputXml, List.of());
            final List<Node> nodes = transformerConfig.getNodes();
            for (Node node : nodes) {
                final CoreService coreService = coreServiceFactory.getCoreService(node.getType());

                transferDetails = coreService.perform(transferDetails, node.getProperties());
                log.info("~~~~result:: " + transferDetails);
            }

        } catch (InvalidInputXmlException e) {
            log.error("Input xml is invalid. Error message: {}", e.getMessage());
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            log.error("Error while applying XSL transformation. Error message: {}", e.getMessage());
        } catch (XPathExpressionException e) {
            log.error("Error occurred while applying XPath. Error message: {}", e.getMessage());
        }

        final long timeTaken = System.currentTimeMillis() - startTime;
        return "Process Completed in ms: " + timeTaken;
    }

}
