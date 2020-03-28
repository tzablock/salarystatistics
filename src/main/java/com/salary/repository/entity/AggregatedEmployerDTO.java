package com.salary.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AggregatedEmployerDTO {
    private String employerName;
    private Double avgSalary;
}
