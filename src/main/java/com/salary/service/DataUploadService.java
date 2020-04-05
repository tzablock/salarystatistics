package com.salary.service;

import com.salary.controller.response.ResponseWrapper;
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

@Service
public class DataUploadService { //TODO unit tests
    private SalariesReposiory salariesReposiory;
    private PositionRepository positionRepo;
    private EmployerRepository employerRepository;
    private RestService restService;

    @Autowired
    public DataUploadService(SalariesReposiory salariesReposiory,
                             PositionRepository positionRepo,
                             EmployerRepository employerRepository,
                             RestService restService) {
        this.salariesReposiory = salariesReposiory;
        this.positionRepo = positionRepo;
        this.employerRepository = employerRepository;
        this.restService = restService;
    }

    public ResponseWrapper requestPositionsWithSalaries(String companyName){
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
            return new ResponseWrapper("");
        } catch (Exception e){
            return new ResponseWrapper(String.format("Internal Server Problem: %s", e.getMessage()));
        }
    }

    public ResponseWrapper uploadEmployer(EmployerDTO employer) {
        return new ResponseWrapper(String.format("Employer with name %s already exist.", employer.getName()))
                                   .invokeOnCondition(employerRepository.ifNotExistByName(employer.getName()),
                                                      () -> employerRepository.save(employer.synchronizeRelation()));
    }

    public ResponseWrapper uploadPosition(PositionDTO position) {
        List<PositionDTO> positions = positionRepo.findByPositionNameAndEmployer_Name(position.getPositionName(),
                                                                                           position.getEmployer().getName());
        return new ResponseWrapper("").invokeOrAlternativeOnCondition(positions.isEmpty(),
                                                                                   () -> positionRepo.save(position.synchronizeRelation()),
                                                                                   () -> {
                                                                                         PositionDTO positionToEdit = positions.get(0);
                                                                                         positionToEdit.addSalaries(position.getSalaries());
                                                                                         positionRepo.save(positionToEdit);
                                                                                          }
                                                                                  );
    }

}
