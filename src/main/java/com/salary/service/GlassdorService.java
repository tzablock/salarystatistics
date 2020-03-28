package com.salary.service;

import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.repository.glassdor.EmployerRepository;
import com.salary.repository.glassdor.PositionRepository;
import com.salary.repository.glassdor.SalariesReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GlassdorService {
    private SalariesReposiory salariesReposiory;

    @Autowired
    public GlassdorService(SalariesReposiory salariesReposiory) {
        this.salariesReposiory = salariesReposiory;
    }

    public Map<String, Map<String, Integer>> getEmployerPositionsSalaries(List<String> employers) {
        return salariesReposiory.selectEmployersPositionsSalaries(employers);
    }

    public Map<String, Map<String, Integer>> getEmployerPositionsSalaries(List<String> employers, List<String> positions) {
        return salariesReposiory.selectEmployersPositionsSalaries(employers, positions);
    }

    public Map<String, Integer> getPositionsSalaries(List<String> positions) {
        return salariesReposiory.selectPositionsSalaries(positions);
    }

    public Map<String, Integer> getEmployerSalaries(String position, List<String> employers) {
        return salariesReposiory.selectEmployerSalaries(position, employers);
    }

    public List<Integer> getSalaries(String employer, String position) {
        return salariesReposiory.selectSalaries(employer, position);
    }
}
