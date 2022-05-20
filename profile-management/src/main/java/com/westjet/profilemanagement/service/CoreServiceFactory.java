package com.westjet.profilemanagement.service;

import com.westjet.profilemanagement.enums.NodeType;
import com.westjet.profilemanagement.service.impl.XPathServiceImpl;
import com.westjet.profilemanagement.service.impl.XsltServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;

@Component
@Slf4j
@RequiredArgsConstructor
public class CoreServiceFactory {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final TransformerFactory transformerFactory;
    private final XPathFactory xPathFactory;


    public CoreService getCoreService(NodeType nodeType) {
        switch (nodeType) {
            case XSLT:
                return new XsltServiceImpl(documentBuilderFactory, transformerFactory);
            case XPATH:
                return new XPathServiceImpl(documentBuilderFactory, xPathFactory);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
