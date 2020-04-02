package com.salary.controller.validate;

import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestValidator {
    private static final String NO_ERROR = "";

    public Optional<ResponseEntity<String>> validateEmployerInput(EmployerDTO employer) {
        String invalidName = checkField(employer.getName() == null, "Name should be set.");
        String invalidCompanyEarning = checkField(employer.getCompanyEarning() == null, "Company Earning should be set.");
        String invalidCompanySize = checkField(employer.getCompanySize() == null, "Company Size should be set.");
        String error = invalidName + invalidCompanyEarning + invalidCompanySize;
        return produceResponseWithErrorOnInvalid(error);
    }

    public Optional<ResponseEntity<String>> validatePositionInput(PositionDTO position) {
        String invalidPositionName = checkField(position.getPositionName() == null, "Name should be set.");
        String invalidSalaries = checkField(position.getSalaries() == null || position.getSalaries().size() == 0, "At least one salary should be set.");
        String invalidEmployer = checkField(position.getEmployer() == null || position.getEmployer().getName() == null || position.getEmployer().getName().isEmpty(),
                                   "Employer name should be set.");
        String error = invalidPositionName + invalidSalaries + invalidEmployer;
        return produceResponseWithErrorOnInvalid(error);
    }

    private String checkField(boolean condition, String errorMsg){
        return condition ? errorMsg : NO_ERROR;
    }

    private Optional<ResponseEntity<String>> produceResponseWithErrorOnInvalid(String err){
        return err.isEmpty() ? Optional.empty() : Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err));
    }
}
