package com.salary.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AggregatedPositionDTO {
    private String positionName;
    private Double avgSalary;
}
