package com.salary.controller;

import com.salary.controller.validate.RequestValidator;
import com.salary.service.DataEditService;
import com.salary.service.DataUploadService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataEditController {  //TODO IT tests
    private DataEditService dataEditService;
    private RequestValidator requestValidator;

    //TODO edit of existing Employer (all fields instead of id and name)
    //TODO edit existing Position (all fields instead of connection with employer, and its name)
}
