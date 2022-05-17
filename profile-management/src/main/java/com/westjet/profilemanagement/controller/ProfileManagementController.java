package com.westjet.profilemanagement.controller;

import com.westjet.profilemanagement.service.XsltService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profilemanagement")
@RequiredArgsConstructor
public class ProfileManagementController {

    private final XsltService xsltService;

    @GetMapping
    public String start() {
        long startTime = System.currentTimeMillis();
        System.out.println("Process started!");
        xsltService.transform();

        final long timeTaken = System.currentTimeMillis() - startTime;
        return "Process Completed in ms: " + timeTaken;
    }

}
