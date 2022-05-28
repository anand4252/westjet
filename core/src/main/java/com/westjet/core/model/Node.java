package com.westjet.core.model;

import com.westjet.core.enums.NodeType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;


@Data
public class Node {

    @NotBlank
    private NodeType type;

    @NotEmpty
    private Map<String, String> properties;
}
