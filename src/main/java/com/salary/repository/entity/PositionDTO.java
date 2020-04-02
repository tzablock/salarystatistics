package com.salary.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   {
 *         "positionName": "...",
 *         "salaries": [...
 *                     ,...],
 *         "avgSalary": ...,
 *         "employer":{
 *           "name": "..."
 *         }
 *    }
 */
@Entity(name = "position")
@Table(name = "position")  //for name in db to be different than reference in hibernate (which we use to queries etc)
@Getter
@Setter
@NoArgsConstructor
public class PositionDTO implements BiDirectionalRelationEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "position_name")
    private String positionName;
    @Column(name = "salaries")
    @ElementCollection
    private List<Integer> salaries;
    @Column(name = "avg_salary")
    private int avgSalary;
    @ManyToOne(cascade = {CascadeType.ALL})  //it make that we don't have to first save to db related employer but we can save position with this employer and it will be added at the same time
    @NotNull
    private EmployerDTO employer;

    public PositionDTO(String positionName, List<Integer> salaries, int avgSalary, EmployerDTO employer) {
        this.positionName = positionName;
        this.salaries = salaries;
        this.avgSalary = avgSalary;
        this.employer = employer;
    }

    @Override
    public PositionDTO synchronizeRelation(){
        this.employer.addPosition(this);
        return this;
    }

    public void addSalary(List<Integer> salaries){
        this.salaries.addAll(salaries);
        this.avgSalary = this.salaries.stream().collect(Collectors.averagingInt(i -> i)).intValue();
    }
}
