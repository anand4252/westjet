package com.westjet.profilemanagement.controller;

import com.example.consumingwebservice.wsdl.GetCountryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westjet.core.config.TransformerConfig;
import com.westjet.core.helper.CoreHelper;
import com.westjet.core.service.CoreServiceFactory;
import com.westjet.profilemanagement.SessionClient;
import com.westjet.profilemanagement.model.SoapResponse;
import com.westjet.profilemanagement.model.wsdl.Body;
import com.westjet.profilemanagement.model.wsdl.Envelope;
import com.westjet.profilemanagement.model.wsdl.Header;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.Security;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRS;
import com.westjet.profilemanagement.temp.CountryClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.EndElement;


@RestController
@RequestMapping("/profilemanagement")
@RequiredArgsConstructor
@Slf4j
public class ProfileManagementController {

    private final TransformerConfig transformerConfig;
    private final CoreHelper coreHelper;
    private final CoreServiceFactory coreServiceFactory;

    private final CountryClient countryClient;
    private final SessionClient sessionClient;

    @GetMapping
    @SneakyThrows
    public String start() {
        long startTime = System.currentTimeMillis();
//        System.out.println("Process started!");
//        final GetCountryResponse response = countryClient.getCountry("Poland");
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ " + response.getCountry().getCurrency());

        final SoapResponse soapResponse = sessionClient.createSession();
        final SessionCreateRS sessionCreateRS = (SessionCreateRS) soapResponse.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        final String value = objectMapper.writeValueAsString(sessionCreateRS);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ");
        System.out.println(value);
        final Security security = (Security) soapResponse.getHeader().get("Security");
        System.out.println(security.getBinarySecurityToken());


//
//        try {
//            String inputXml = coreHelper.getInputXml(transformerConfig.getInputXmlPath());
//            TransferDetails transferDetails = new TransferDetails(inputXml, List.of());
//            final List<Node> nodes = transformerConfig.getNodes();
//            for (Node node : nodes) {
//                final CoreService coreService = coreServiceFactory.getCoreService(node.getType());
//
//                transferDetails = coreService.perform(transferDetails, node.getProperties());
//                log.info("~~~~result:: " + transferDetails);
//            }
//
//        } catch (InvalidInputXmlException e) {
//            log.error("Input xml is invalid. Error message: {}", e.getMessage());
//        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
//            log.error("Error while applying XSL transformation. Error message: {}", e.getMessage());
//        } catch (XPathExpressionException e) {
//            log.error("Error occurred while applying XPath. Error message: {}", e.getMessage());
//        }

        final long timeTaken = System.currentTimeMillis() - startTime;
        return "Process Completed in ms: " + timeTaken;
    }


}
