package com.westjet.profilemanagement.controller;

import com.westjet.profilemanagement.config.TransformerConfig;
import com.westjet.profilemanagement.exception.InvalidInputXmlException;
import com.westjet.profilemanagement.model.Node;
import com.westjet.profilemanagement.service.CoreHelper;
import com.westjet.profilemanagement.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/profilemanagement")
@RequiredArgsConstructor
@Slf4j
public class ProfileManagementController {

    private final TransformerConfig transformerConfig;
    private final CoreHelper coreHelper;
    private final CoreService xsltService;

    private final CoreService xPathService;

    @GetMapping
    public String start() {
        long startTime = System.currentTimeMillis();
        System.out.println("Process started!");
        try {
            String inputXml = coreHelper.getInputXml(transformerConfig.getInputXmlPath());
            final List<Node> nodes = transformerConfig.getNodes();

            for (Node node : nodes) {
                inputXml = xsltService.perform(inputXml, node.getProperties(), List.of());
                log.info("~~~~Transformed result:: " + inputXml);
            }
            Map<String, String> map = new HashMap<>();
            map.put("xpathExpression", "xerris/location/vancouver/name");
            final String xpathResult = xPathService.perform(inputXml, map, List.of());
            log.info("xpathResulttttt: " + xpathResult);

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
