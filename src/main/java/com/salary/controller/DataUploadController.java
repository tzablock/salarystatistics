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
        return mapUploadResultAsResponse(dataUploadService.requestPositionsWithSalaries(companyName),
                String.format("Positions with salaries retrieved for company: %s", companyName));
    }

    @PostMapping("/dataUpload/employer")
    public ResponseEntity<String> uploadEmployer(@RequestBody EmployerDTO employer) {
        Optional<ResponseEntity<String>> inputInvalidResponseOpt = this.requestValidator.validateEmployerInput(employer);
        return inputInvalidResponseOpt.orElse(
                mapUploadResultAsResponse(dataUploadService.uploadEmployer(employer),
                        String.format("Employer with %s name %d size %d earnings was added.",
                                employer.getName(),
                                employer.getCompanySize(),
                                employer.getCompanyEarning()))
        );
    }

    @PostMapping("/dataUpload/position")
    public ResponseEntity<String> uploadPosition(@RequestBody PositionDTO position) {
        Optional<ResponseEntity<String>> inputInvalidResponseOpt = this.requestValidator.validatePositionInput(position);
        return inputInvalidResponseOpt.orElse(
                mapUploadResultAsResponse(dataUploadService.uploadPosition(position),
                        String.format("Position with %s name %s salaries %d avg salary was added/updated.",
                                position.getPositionName(),
                                position.getSalaries().stream()
                                        .map(Object::toString)
                                        .collect(Collectors.joining(",")),
                                position.getAvgSalary()))
        );
    }

    //TODO edit /dataUpload/employer
    //TODO edit /dataUpload/position

    private ResponseEntity<String> mapUploadResultAsResponse(Optional<String> result, String successMessage) {
        return result.map(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
                .orElse(ResponseEntity.ok(successMessage));
    }
    //TODO position with salary from fields for employer

    //TODO employers from excel
    //TODO positions with salaries for employers from excel
}
