package com.westjet.profilemanagement.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class Node {
    @NotBlank
    private String inputPath;

    @NotBlank
    private String outputPath;

    @NotBlank
    private String xsltPath;
}
