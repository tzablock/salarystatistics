package com.salary.service;

import com.salary.controller.response.ResponseWrapper;
import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.repository.glassdor.EmployerRepository;
import com.salary.repository.glassdor.PositionRepository;
import com.salary.repository.glassdor.SalariesReposiory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataUploadServiceTest {
    private DataUploadService out;
    @Mock
    private SalariesReposiory salariesReposiory;
    @Mock
    private PositionRepository positionRepo;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private RestService restService;

    @Test
    void uploadEmployer() {

    }

    @Test
    void uploadPositionShouldSaveNewPositionAndReturnPositiveResponseWhenPositionWithSuchPositionNameAndEmployerNameNotExist() {
        PositionDTO newPosition = preparePositionInput(Collections.emptyList());
        prepareMocksForPositionRepo(newPosition, Collections.emptyList());
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseWrapper response = this.out.uploadPosition(newPosition);

        verify(positionRepo, times(1)).save(newPosition);
        assertThat(response.httpFormat("Position Saved!").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void uploadPositionShouldUpdatePositionAndReturnPositiveResponseWhenPositionWithSuchPositionNameAndEmployerNameExist() {
        PositionDTO updateFromPosition = preparePositionInput(Collections.singletonList(1000));
        PositionDTO positionToUpdate = preparePositionInput(Collections.singletonList(2000));
        prepareMocksForPositionRepo(updateFromPosition, Collections.singletonList(positionToUpdate));
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseWrapper response = this.out.uploadPosition(updateFromPosition);

        verify(positionRepo, times(1)).save(positionToUpdate);
        assertThat(positionToUpdate.getSalaries()).contains(1000).contains(2000);
        assertThat(positionToUpdate.getAvgSalary()).isEqualTo(1500);
        assertThat(response.httpFormat("Position Updated!").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void uploadPositionShouldReturnINTERNAL_SERVER_ERRORWhenSomeExceptionIsThrown() {
        PositionDTO newPosition = preparePositionInput(Collections.emptyList());
        prepareMocksForPositionRepo(newPosition, Collections.emptyList());
        prepareMocksForPositionRepoThrowingException(newPosition);
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseWrapper response = this.out.uploadPosition(newPosition);

        assertThat(response.httpFormat("").getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //TODO make as well exception test

    private PositionDTO preparePositionInput(List<Integer> salaries) {
        PositionDTO newPosition = new PositionDTO();
        newPosition.setPositionName("PositionName");
        newPosition.addSalaries(salaries);
        EmployerDTO newPositionEmployer = new EmployerDTO();
        newPositionEmployer.setName("Name");
        newPosition.setEmployer(newPositionEmployer);
        return newPosition;
    }

    private void prepareMocksForPositionRepo(PositionDTO position, List<PositionDTO> findResponse){
        Mockito.when(positionRepo.findByPositionNameAndEmployer_Name(position.getPositionName(), position.getEmployer().getName()))
                .thenReturn(findResponse);
    }

    private void prepareMocksForPositionRepoThrowingException(PositionDTO position){
        Mockito.when(positionRepo.save(position))
                .thenThrow(RuntimeException.class);
    }
}