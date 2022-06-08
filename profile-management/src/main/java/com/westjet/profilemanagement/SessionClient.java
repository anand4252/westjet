package com.westjet.profilemanagement;

import com.westjet.profilemanagement.model.wsdl.Envelope;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRS;
import com.westjet.profilemanagement.builder.SessionCreateBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


@Slf4j
@RequiredArgsConstructor
public class SessionClient extends WebServiceGatewaySupport {
    final private SessionCreateBuilder sessionCreateService;

    @SneakyThrows
    public SessionCreateRS createSession() {

        final Envelope envelope = sessionCreateService.buildEnvelope();


        //Print xml
//        StringWriter sw = new StringWriter();
//        JAXB.marshal(envelope, sw);
//        String xmlString = sw.toString();

        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        this.getMarshaller().marshal(envelope, result);
        String xmlString = sw.toString();

        System.out.println("xmlString::: ");
        System.out.println(xmlString);


        final Object response = getWebServiceTemplate().marshalSendAndReceive("https://sws-crt-as.cert.havail.sabre.com/", envelope);

        return (SessionCreateRS) response;
    }


}
