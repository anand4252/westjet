package com.westjet.profilemanagement.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class SoapResponse {

    private final Map<String, Object> header;
    private final Object body;

}
