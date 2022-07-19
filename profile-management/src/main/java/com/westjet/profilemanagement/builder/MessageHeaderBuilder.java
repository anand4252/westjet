package com.westjet.profilemanagement.builder;

import com.westjet.profilemanagement.model.wsdl.From;
import com.westjet.profilemanagement.model.wsdl.MessageHeader;
import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.PartyId;
import com.westjet.profilemanagement.model.wsdl.Service;
import com.westjet.profilemanagement.model.wsdl.To;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHeaderBuilder {

    final private ObjectFactory objectFactory;

    public MessageHeader buildMessageHeader() {
        final MessageHeader messageHeader = objectFactory.createMessageHeader();

        messageHeader.setVersion("1.0");

        messageHeader.setFrom(buildFrom());
        messageHeader.setTo(buildTo());
        messageHeader.setConversationId("abc123");
        messageHeader.setCPAId("Sabre");
        messageHeader.setService(buildService("string", "Session"));
        messageHeader.setAction("SessionCreateRQ");
//        messageHeader.setMessageData(buildMessageData()); TODO Not mandatory.

        return messageHeader;
    }

    private PartyId buildPartyId(String type, String value) {
        final PartyId partyId = objectFactory.createPartyId();
        partyId.setType(type);
        partyId.setValue(value);

        return partyId;
    }

    private From buildFrom() {
        final From from = objectFactory.createFrom();
        from.getPartyId().add(buildPartyId("URI", "IPS.westjet.com"));
        return from;
    }

    private To buildTo() {
        final To to = objectFactory.createTo();
        to.getPartyId().add(buildPartyId("URI", "webservices.sabre.com"));

        return to;
    }

    private Service buildService(String type, String value) {
        final Service service = objectFactory.createService();
        service.setType(type);
        service.setValue(value);

        return service;
    }
}
