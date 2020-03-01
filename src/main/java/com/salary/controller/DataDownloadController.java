package com.salary.controller;

import com.salary.service.GlassdorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataDownloadController {
    private GlassdorService glassdor;

    @Autowired
    public DataDownloadController(GlassdorService glassdor) {
        this.glassdor = glassdor;
    }
    //@PathVariable("some") String some> @GetMapping(value = "/glassdor/downloadData/{some}") > /glassdor/downloadData/dududud
    //@RequestParam("employers") List<String> list > @GetMapping(value = "/glassdor/downloadData") > /glassdor/downloadData/ddddd?employers=first&employers=second&employers=third
    @GetMapping(value = "/glassdor/downloadData")
    public void triggerDownloadingDataToDB(){
        glassdor.requestPositions("");
    }
}
