package com.westjet.profilemanagement.service.impl;

import com.westjet.profilemanagement.exception.InvalidInputXmlException;
import com.westjet.profilemanagement.service.CoreHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Slf4j
public class CoreHelperImpl implements CoreHelper {

    @Override
    public String getInputXml(String xmlPath) throws InvalidInputXmlException {
        Path filePath = Path.of(xmlPath);
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading input xml.", e);
        }

        if (!StringUtils.hasText(content)) {
            throw new InvalidInputXmlException("Input xml is empty.");
        }
        log.info("Input xml:: " + content);

        return content;
    }
}
