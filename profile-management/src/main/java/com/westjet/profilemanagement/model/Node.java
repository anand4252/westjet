package com.westjet.profilemanagement.model;

import com.westjet.profilemanagement.enums.NodeType;
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
