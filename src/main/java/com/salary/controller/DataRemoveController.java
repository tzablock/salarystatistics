package com.salary.controller;

import com.salary.controller.validate.RequestValidator;
import com.salary.service.DataRemoveService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataRemoveController {  //TODO IT tests
    private DataRemoveService dataRemoveService;
    private RequestValidator requestValidator;

    //TODO remove Employer by name and corresponding positions
    //TODO remove Position by name and Employer name
}
