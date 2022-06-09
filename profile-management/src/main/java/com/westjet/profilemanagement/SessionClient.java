package com.westjet.profilemanagement;

import com.westjet.profilemanagement.builder.MessageHeaderBuilder;
import com.westjet.profilemanagement.builder.SecurityBuilder;
import com.westjet.profilemanagement.model.wsdl.Envelope;
import com.westjet.profilemanagement.model.wsdl.Header;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.Security;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRQ;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRS;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


@Slf4j
@RequiredArgsConstructor
public class SessionClient extends WebServiceGatewaySupport {
    final private MessageHeaderBuilder messageHeaderBuilder;

    final private SecurityBuilder securityBuilder;

    @SneakyThrows
    public SessionCreateRS createSession() {

//        final Envelope envelope = sessionCreateService.buildEnvelope();
        final SessionCreateRQ sessionCreateRQ = buildSessionCreateRQ();


        //Print xml
//        StringWriter sw = new StringWriter();
//        JAXB.marshal(envelope, sw);
//        String xmlString = sw.toString();

//        StringWriter sw = new StringWriter();
//        Result result = new StreamResult(sw);
//        this.getMarshaller().marshal(envelope, result);
//        String xmlString = sw.toString();
//
//        System.out.println("xmlString::: ");
//        System.out.println(xmlString);


        final Object response = getWebServiceTemplate().marshalSendAndReceive("https://sws-crt-as.cert.havail.sabre.com/", sessionCreateRQ, message -> {
            if (message == null) {
                return;
            } else if (message instanceof SaajSoapMessage) {
                final SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
                final SoapHeader soapHeader = saajSoapMessage.getSoapHeader();

                final MessageHeader messageHeader = messageHeaderBuilder.buildMessageHeader();
                this.getMarshaller().marshal(messageHeader, soapHeader.getResult());

                final Security security = securityBuilder.buildSecurity();
                this.getMarshaller().marshal(security, soapHeader.getResult());
//                soapHeader.addHeaderElement()
            }
        });

        return (SessionCreateRS) response;
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
