package com.westjet.profilemanagement.service;

import com.westjet.profilemanagement.exception.InvalidInputXmlException;

public interface CoreHelper {
    String getInputXml(String path) throws InvalidInputXmlException;
}
