package com.salary.controller;

import com.salary.repository.entity.EmployerDTO;
import com.salary.service.DataUploadService;
import com.salary.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DataDownloadController {
    private SalaryService salaryService;

    @Autowired
    public DataDownloadController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/positionssalaries/employer/{employers}")
    public Map<String, Map<String, Integer>> returnPositionsWithSalariesForEmployers(@PathVariable("employers") List<String> employers){
        return salaryService.getEmployerPositionsSalaries(employers); //employer_name position_name avg_salary
    }

    @GetMapping("/positionssalaries/{employers}/{positions}")
    public Map<String, Map<String, Integer>> returnPositionsWithSalariesForEmployersAndPositions(@PathVariable("employers") List<String> employers,
                                                                                                 @PathVariable("positions") List<String> positions){
        return salaryService.getEmployerPositionsSalaries(employers, positions); //employer_name position_name avg_salary
    }

    @GetMapping("/positionssalaries/allEmployers/{positions}")
    public Map<String, Integer> returnPositionsSalaryForAllEmployers(@PathVariable("positions") List<String> positions){
        return salaryService.getPositionsSalaries(positions); //position_name avg_salary
    }

    @GetMapping("/positioncomparision/{employers}/{position}")
    public Map<String, Integer> returnComparisionOfPositionForEmployers(@PathVariable("position") String position,
                                                                        @PathVariable("employers") List<String> employers){
        return salaryService.getEmployerSalaries(position, employers); // Employer_name avg_Salary
    }

    @GetMapping("/allsalaries/{employer}/{position}")
    public List<Integer> returnSalariesForEmployerAndPosition(@PathVariable("employer") String employer,
                                                              @PathVariable("position") String position){
        return salaryService.getSalaries(employer, position); //salaries(not aggregated)
    }
}
