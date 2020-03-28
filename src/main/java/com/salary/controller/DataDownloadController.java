package com.salary.controller;

import com.salary.service.DataUploadGlassdorService;
import com.salary.service.GlassdorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DataDownloadController {
    private GlassdorService glassdor;
    private DataUploadGlassdorService dataUploadGlassdorService;

    @Autowired
    public DataDownloadController(GlassdorService glassdor, DataUploadGlassdorService dataUploadGlassdorService) {
        this.glassdor = glassdor;
        this.dataUploadGlassdorService = dataUploadGlassdorService;
    }
    //@PathVariable("some") String some> @GetMapping(value = "/glassdor/downloadData/{some}") > /glassdor/downloadData/dududud
    //@RequestParam("employers") List<String> list > @GetMapping(value = "/glassdor/downloadData") > /glassdor/downloadData/ddddd?employers=first&employers=second&employers=third
    @GetMapping("/glassdor/downloadData")
    public ResponseEntity<String> triggerDownloadingDataToDB(@RequestParam(value = "companyName", required = false) String companyName){
        return dataUploadGlassdorService.requestPositionsWithSalaries(companyName)
                .map(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
                .orElse(ResponseEntity.ok(String.format("Positions with salaries retrieved for company: %s", companyName)));

    }

    //TODO add Manual providing data
    //TODO employer from fields
    //TODO position with salary from fields for employer

    //TODO employers from excel
    //TODO positions with salaries for employers from excel

    @GetMapping("/glassdor/positionssalaries/employer/{employers}")
    public Map<String, Map<String, Integer>> returnPositionsWithSalariesForEmployers(@PathVariable("employers") List<String> employers){
        return glassdor.getEmployerPositionsSalaries(employers); //employer_name position_name avg_salary
    }

    @GetMapping("/glassdor/positionssalaries/{employers}/{positions}")
    public Map<String, Map<String, Integer>> returnPositionsWithSalariesForEmployersAndPositions(@PathVariable("employers") List<String> employers,
                                                                                                 @PathVariable("positions") List<String> positions){
        return glassdor.getEmployerPositionsSalaries(employers, positions); //employer_name position_name avg_salary
    }

    @GetMapping("/glassdor/positionssalaries/allEmployers/{positions}")
    public Map<String, Integer> returnPositionsSalaryForAllEmployers(@PathVariable("positions") List<String> positions){
        return glassdor.getPositionsSalaries(positions); //position_name avg_salary
    }

    @GetMapping("/glassdor/positioncomparision/{employers}/{position}")
    public Map<String, Integer> returnComparisionOfPositionForEmployers(@PathVariable("position") String position,
                                                                        @PathVariable("employers") List<String> employers){
        return glassdor.getEmployerSalaries(position, employers); // Employer_name avg_Salary
    }

    @GetMapping("/glassdor/allsalaries/{employer}/{position}")
    public List<Integer> returnSalariesForEmployerAndPosition(@PathVariable("employer") String employer,
                                                              @PathVariable("position") String position){
        return glassdor.getSalaries(employer, position); //salaries(not aggregated)
    }
}
