package com.salary.controller;

import com.salary.controller.validate.RequestValidator;
import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.service.DataUploadService;
import com.salary.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DataUploadController {
    private DataUploadService dataUploadService;
    private RequestValidator requestValidator;

    @Autowired
    public DataUploadController(DataUploadService dataUploadService, RequestValidator requestValidator) {
        this.dataUploadService = dataUploadService;
        this.requestValidator = requestValidator;
    }

    //@PathVariable("some") String some> @GetMapping(value = "/glassdor/downloadData/{some}") > /glassdor/downloadData/dududud
    //@RequestParam("employers") List<String> list > @GetMapping(value = "/glassdor/downloadData") > /glassdor/downloadData/ddddd?employers=first&employers=second&employers=third
    @GetMapping("/glassdor/downloadData")
    public ResponseEntity<String> triggerDownloadingDataToDB(@RequestParam(value = "companyName", required = false) String companyName) {
        return dataUploadService.requestPositionsWithSalaries(companyName)
                                .httpFormat(String.format("Positions with salaries retrieved for company: %s", companyName));
    }

    @PostMapping("/dataUpload/employer")
    public ResponseEntity<String> uploadEmployer(@RequestBody EmployerDTO employer) {
        Optional<ResponseEntity<String>> inputInvalidResponseOpt = this.requestValidator.validateEmployerInput(employer);
        return inputInvalidResponseOpt.orElse(
                dataUploadService.uploadEmployer(employer)
                                 .httpFormat(String.format("Employer with %s name %d size %d earnings was added.",
                                                           employer.getName(),
                                                           employer.getCompanySize(),
                                                           employer.getCompanyEarning()))
        );
    }

    @PostMapping("/dataUpload/position")
    public ResponseEntity<String> uploadPosition(@RequestBody PositionDTO position) {
        Optional<ResponseEntity<String>> inputInvalidResponseOpt = this.requestValidator.validatePositionInput(position);
        return inputInvalidResponseOpt.orElse(
                dataUploadService.uploadPosition(position)
                        .httpFormat(String.format("Position with %s for employer %s name %s salaries was added/updated.",
                                                  position.getPositionName(),
                                                  position.getEmployer().getName(),
                                                  position.getSalaries().stream()
                                                                        .map(Object::toString)
                                                                        .collect(Collectors.joining(","))))
        );
    }

    //TODO upload excel file and from it extract multiple employers like /dataUpload/employer
    //TODO upload excel file and from it extract multiple positions with salaries like /dataUpload/position
}
