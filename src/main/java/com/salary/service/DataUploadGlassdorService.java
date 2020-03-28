package com.salary.service;

import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.repository.glassdor.EmployerRepository;
import com.salary.repository.glassdor.PositionRepository;
import com.salary.repository.glassdor.SalariesReposiory;
import com.salary.service.entity.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DataUploadGlassdorService {
    private SalariesReposiory salariesReposiory;
    private PositionRepository positionRepo;
    private EmployerRepository employerRepository;
    private RestService restService;

    @Autowired
    public DataUploadGlassdorService(SalariesReposiory salariesReposiory, PositionRepository positionRepo, EmployerRepository employerRepository, RestService restService) {
        this.salariesReposiory = salariesReposiory;
        this.positionRepo = positionRepo;
        this.employerRepository = employerRepository;
        this.restService = restService;
    }

    public Optional<String> requestPositionsWithSalaries(String companyName){
        try {
            //TODO http request to this domain
            //http://api.glassdoor.com/api/api.htm?v=1&format=json&t.p=120&t.k=fz6JLNDfgVs&action=employers&q=pharmaceuticals&userip=192.168.43.42&useragent=Mozilla/%2F4.0
            //TODO save all results into db
            EmployerDTO empl = new EmployerDTO("EM1", 3000, 1000000);
            PositionDTO position = new PositionDTO("POS2", Arrays.asList(5000, 4000), 9500, empl);
            List<PositionDTO> positions = new ArrayList<>();
            positions.add(position);
            empl.setPositions(positions);
            positionRepo.save(position);
            //TODO request for companyName if not exist than return "No data for such company"
            //TODO if request fail than return
            List<Employer> empls = restService.getEmployers("Nov");
            System.out.println("DADA");
            return Optional.empty();
        } catch (Exception e){
            return Optional.of(String.format("Internal Server Problem: %s", e.getMessage()));
        }
    }

    //Eployers list
    //https://www.glassdoor.com/searchsuggest/typeahead?source=Review&version=New&input=Nov&rf=full
    //Countries:
    //https://www.glassdoor.com/findPopularLocationAjax.htm?term=Z&maxLocationsToReturn=10


    //https://de.glassdoor.ch/Gehalt/Novartis-Medical-Science-Liaison-Z%C3%BCrich-Geh%C3%A4lter-EJI_IE6667.0,8_KO9,32_IL.33,39_IM1144.htm
    //Employeer
    //6-209
    //Position:
//1050-1083 !!
//i740 ?

}
