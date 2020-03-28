package com.salary.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "employer")
@Entity(name = "employer")
@Getter
@Setter
@NoArgsConstructor
public class EmployerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "company_size")
    private int companySize;
    @Column(name = "company_earning")
    private long companyEarning;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<PositionDTO> positions;


    public EmployerDTO(String name, int companySize, long companyEarning) {
        this.name = name;
        this.companySize = companySize;
        this.companyEarning = companyEarning;
    }
}
