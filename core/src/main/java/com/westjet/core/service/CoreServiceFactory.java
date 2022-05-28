package com.westjet.core.service;

import com.westjet.core.enums.NodeType;
import com.westjet.core.helper.CoreHelper;
import com.westjet.core.service.impl.XPathServiceImpl;
import com.westjet.core.service.impl.XsltServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;

@Component
@Slf4j
@RequiredArgsConstructor
public class CoreServiceFactory {

    private final CoreHelper coreHelper;
    private final TransformerFactory transformerFactory;
    private final XPathFactory xPathFactory;


    public CoreService getCoreService(NodeType nodeType) {
        switch (nodeType) {
            case XSLT:
                return new XsltServiceImpl(coreHelper, transformerFactory);
            case XPATH:
                return new XPathServiceImpl(coreHelper, xPathFactory);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
