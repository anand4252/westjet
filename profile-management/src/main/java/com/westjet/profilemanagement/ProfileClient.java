package com.westjet.profilemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westjet.profilemanagement.builder.MessageHeaderBuilder;
import com.westjet.profilemanagement.builder.SecurityBuilder;
import com.westjet.profilemanagement.model.SoapResponse;
import com.westjet.profilemanagement.model.wsdl.Body;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.Security;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRQ;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
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

/**
 * Work in progress. Use SessionClient as reference
 */

@Slf4j
@RequiredArgsConstructor
public class ProfileClient extends WebServiceGatewaySupport {
    final private MessageHeaderBuilder messageHeaderBuilder;

    private final ObjectFactory objectFactory;

    @SneakyThrows
    public SoapResponse read(String securityToken) {

        final MessageHeader messageHeader = messageHeaderBuilder.buildMessageHeader();

        final Security security = objectFactory.createSecurity();
        security.setBinarySecurityToken(securityToken);

        final Body body = objectFactory.createBody();



        SoapResponse soapResponse = getWebServiceTemplate().sendAndReceive("https://sws-crt-as.cert.havail.sabre.com/",
                new WebServiceMessageCallback() {
                    public void doWithMessage(WebServiceMessage message) throws IOException {
                        if (message instanceof SaajSoapMessage) {
                            final SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
                            final SoapHeader soapHeader = saajSoapMessage.getSoapHeader();

                            getWebServiceTemplate().getMarshaller().marshal(messageHeader, soapHeader.getResult());
                            MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), null, message);


                            getWebServiceTemplate().getMarshaller().marshal(security, soapHeader.getResult());
                            MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), null, message);
//                soapHeader.addHeaderElement()
                        }
                    }
                },
                new WebServiceMessageExtractor<SoapResponse>() {
                    public SoapResponse extractData(WebServiceMessage message) throws IOException {

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
                });

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("##############################" + objectMapper.writeValueAsString(soapResponse));

//        final Object response = getWebServiceTemplate().marshalSendAndReceive("https://sws-crt-as.cert.havail.sabre.com/", sessionCreateRQ, message -> {
//            if (message instanceof SaajSoapMessage) {
//                final SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
//                final SoapHeader soapHeader = saajSoapMessage.getSoapHeader();
//
//                final MessageHeader messageHeader = messageHeaderBuilder.buildMessageHeader();
//                this.getMarshaller().marshal(messageHeader, soapHeader.getResult());
//
//                final Security security = securityBuilder.buildSecurity();
//                this.getMarshaller().marshal(security, soapHeader.getResult());
////                soapHeader.addHeaderElement()
//            }
//        });

        return soapResponse;
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
