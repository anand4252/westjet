package com.westjet.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@ToString
public class TransferDetails {

    private final String input;

    //Holds the values that are passed between Nodes
    private final List<Map<String, Object>> params;
}
