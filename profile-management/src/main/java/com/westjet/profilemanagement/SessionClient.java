package com.westjet.profilemanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westjet.profilemanagement.builder.MessageHeaderBuilder;
import com.westjet.profilemanagement.builder.SecurityBuilder;
import com.westjet.profilemanagement.model.SoapResponse;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.Security;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class SessionClient extends WebServiceGatewaySupport {
    final private MessageHeaderBuilder messageHeaderBuilder;

    final private SecurityBuilder securityBuilder;


    public SoapResponse createSession() {

        final SessionCreateRQ sessionCreateRQ = buildSessionCreateRQ();


        SoapResponse soapResponse = getWebServiceTemplate().sendAndReceive("https://sws-crt-as.cert.havail.sabre.com/",
                message -> extracted(sessionCreateRQ, message),
                this::getSoapResponse);

        val objectMapper = new ObjectMapper();
        try {
            log.info("Sabre Session successfully created. " + objectMapper.writeValueAsString(soapResponse));
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Error occurred while parsing the Sabre session.");
        }

        return soapResponse;
    }


    private void extracted(SessionCreateRQ sessionCreateRQ, WebServiceMessage message) throws IOException {
        if (message instanceof SaajSoapMessage) {
            final SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
            final SoapHeader soapHeader = saajSoapMessage.getSoapHeader();

            final MessageHeader messageHeader = messageHeaderBuilder.buildMessageHeader();
            getWebServiceTemplate().getMarshaller().marshal(messageHeader, soapHeader.getResult());
            MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), sessionCreateRQ, message);


            final Security security = securityBuilder.buildSecurity();
            getWebServiceTemplate().getMarshaller().marshal(security, soapHeader.getResult());
            MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), sessionCreateRQ, message);
        }
    }

    private SoapResponse getSoapResponse(WebServiceMessage message) throws IOException {
        final SoapMessage soapMessage = (SoapMessage) message;
        SoapHeader header = soapMessage.getSoapHeader();


        Iterator<SoapHeaderElement> iterator = header.examineAllHeaderElements();//header.examineHeaderElements(new QName("urn:test", "ResponseHeader"));
        final Unmarshaller unmarshaller = getWebServiceTemplate().getUnmarshaller();
        Map<String, Object> headers = new HashMap<>();

        while (iterator.hasNext()) {
            final SoapHeaderElement headerElement = iterator.next();
            headers.put(headerElement.getName().getLocalPart(),
                    unmarshaller.unmarshal(headerElement.getSource()));
        }

        return new SoapResponse(headers, MarshallingUtils.unmarshal(unmarshaller, message));
    }


    private SessionCreateRQ buildSessionCreateRQ() {
        val source = new SessionCreateRQ.POS.Source();
        source.setPseudoCityCode("YCB");

        val pos = new SessionCreateRQ.POS();
        pos.setSource(source);

        val sessionCreateRQ = new SessionCreateRQ();
        sessionCreateRQ.setPOS(pos);
        return sessionCreateRQ;
    }


}
