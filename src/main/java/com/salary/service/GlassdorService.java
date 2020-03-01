package com.salary.service;

import com.salary.repository.entity.Employer;
import com.salary.repository.entity.Position;
import com.salary.repository.glassdor.EmployerRepository;
import com.salary.repository.glassdor.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class GlassdorService {
    private EmployerRepository employers;
    private PositionRepository positions;

    @Autowired
    public GlassdorService(EmployerRepository employers, PositionRepository positions) {
        this.employers = employers;
        this.positions = positions;
    }

    public Map<String, Double> requestPositions(String companyName){
        //TODO http request to this domain
        //http://api.glassdoor.com/api/api.htm?v=1&format=json&t.p=120&t.k=fz6JLNDfgVs&action=employers&q=pharmaceuticals&userip=192.168.43.42&useragent=Mozilla/%2F4.0

        positions.save(new Position("testPositionName", Arrays.asList(5000,4000), 4500,new Employer()));
        return null;
    }
}
