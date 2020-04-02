package com.salary.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
//    {
//        "name": "...",
//        "companySize": ...,
//        "companyEarning": ...
//    }
 */
@Table(name = "employer")
@Entity(name = "employer")
@Getter
@Setter
@NoArgsConstructor
public class EmployerDTO implements BiDirectionalRelationEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "company_size")
    private Integer companySize;
    @Column(name = "company_earning")
    private Long companyEarning;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<PositionDTO> positions;


    public EmployerDTO(String name, int companySize, long companyEarning) {
        this.name = name;
        this.companySize = companySize;
        this.companyEarning = companyEarning;
    }

    public void addPosition(PositionDTO position){
        if (this.positions == null){
            this.positions = Collections.singletonList(position);
        } else {
            this.positions.add(position);
        }
    }

    @Override
    public EmployerDTO synchronizeRelation() {
        if (this.positions != null){
            this.positions.forEach(p -> p.setEmployer(this));
        }
        return this;
    }
}
