package com.salary.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//@Table(name = "employer")
@Entity(name = "employer")
@Getter
@Setter
@NoArgsConstructor
public class Employer {
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
    @OneToMany
    private List<Position> positions;
}
