package com.westjet.profilemanagement.service.impl;

import com.westjet.core.helper.CoreHelper;
import com.westjet.core.model.TransferDetails;
import com.westjet.core.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("xPathService")
@Slf4j
@RequiredArgsConstructor
public class XPathServiceImpl implements CoreService {
    public static final String XPATH_EXPRESSION = "xpathExpression";

    private final CoreHelper coreHelper;

    private final XPathFactory xPathFactory;

    @Override
    public TransferDetails perform(TransferDetails transferDetails, Map<String, String> properties) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        final Document xmlDocument = coreHelper.getDocument(transferDetails);
        final XPathExpression xPathExpression = getXPathExpression(properties);
        final NodeList nodeList = (NodeList) xPathExpression.evaluate(xmlDocument, XPathConstants.NODESET);

        val selectedNodes = NodeListToString(nodeList);
        final List<Map<String, Object>> paramsFromPreviousService = transferDetails.getParams();
        // Add new params for the next service if any.

        return new TransferDetails(selectedNodes, paramsFromPreviousService);
    }


    private XPathExpression getXPathExpression(Map<String, String> properties) throws XPathExpressionException {
        final XPath xPath = xPathFactory.newXPath();
        return xPath.compile(properties.get(XPATH_EXPRESSION));
    }

    private String NodeListToString(NodeList nodeList) {
        val stringBuilder = new StringBuilder();
        for (int i = 0; i < nodeList.getLength(); i++) {
            stringBuilder.append(nodeList.item(i).getFirstChild().getNodeValue());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
