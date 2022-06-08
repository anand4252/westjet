package com.westjet.profilemanagement.builder;

import com.westjet.profilemanagement.model.wsdl.Body;
import com.westjet.profilemanagement.model.wsdl.Envelope;
import com.westjet.profilemanagement.model.wsdl.Header;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.Security;
import com.westjet.profilemanagement.model.wsdl.SessionCreateRQ;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionCreateBuilder {
    final private ObjectFactory objectFactory;

    final private MessageHeaderBuilder messageHeaderBuilder;

    final private SecurityBuilder securityBuilder;

    public Envelope buildEnvelope() {

        final Envelope envelope = objectFactory.createEnvelope();
        envelope.getHeader().add(buildHeader());

        final Body body = buildBody(buildSessionCreateRQ());
        envelope.setBody(body);

        return envelope;
    }

    private Header buildHeader() {
        ObjectFactory objectFactory = new ObjectFactory();

        final Header header = objectFactory.createHeader();

        header.getAny().add(messageHeaderBuilder.buildMessageHeader());
        header.getAny().add(securityBuilder.buildSecurity());
        //missing UID uid:TraceabilityID TODO Not mandatory.
        return header;
    }











    private Body buildBody(SessionCreateRQ sessionCreateRQ) {
        ObjectFactory objectFactory = new ObjectFactory();
        final Body body = objectFactory.createBody();
        body.getAny().add(sessionCreateRQ);
        return body;
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
